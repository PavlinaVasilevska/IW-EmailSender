package com.example.EmailSender.service.impl;

import com.example.EmailSender.domain.*;
import com.example.EmailSender.dto.EmailJobDTO;
import com.example.EmailSender.enumeration.FrequencyEnum;
import com.example.EmailSender.enumeration.StatusEnum;
import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.mapper.EmailJobMapper;
import com.example.EmailSender.repository.*;
import com.example.EmailSender.service.EmailJobService;
import com.example.EmailSender.service.EmailTemplateService;
import com.example.EmailSender.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailJobServiceImpl implements EmailJobService {

    private final EmailJobRepository emailJobRepository;
    private final EmailJobMapper emailJobMapper;
    private final UserService userService;
    private final EmailTemplateService emailTemplateService;
    private final OccurrenceRepository occurrenceRepository;

    @Override
    public EmailJobDTO createEmailJob(EmailJobDTO emailJobDTO) {
        EmailJob emailJob = emailJobMapper.toEntity(emailJobDTO);

        EmailTemplate emailTemplate = emailTemplateService.getTemplateByUuid(emailJobDTO.getEmailTemplate().getUuid());
        if (emailTemplate == null) {
            throw new ResourceNotFoundException("Email template not found");
        }

        User sender = userService.getByUuid(emailJobDTO.getSender().getUuid());
        if (sender == null) {
            throw new ResourceNotFoundException("Sender not found");
        }

        emailJob.setEmailTemplate(emailTemplate);
        emailJob.setSender(sender);
        emailJob.setFrequency(emailJobDTO.getFrequency());
        emailJob.setStartDate(emailJobDTO.getStartDate());
        emailJob.setEndDate(emailJobDTO.getEndDate());
        emailJob.setEnabled(emailJobDTO.getEnabled());
        emailJob.setReceivers(emailJobDTO.getReceivers());

        EmailJob savedEmailJob = emailJobRepository.save(emailJob);

        return emailJobMapper.toDto(savedEmailJob);
    }

    @Override
    public EmailJobDTO getEmailJobDtoByUuid(String uuid) {
        EmailJob emailJob = emailJobRepository.findByUuid(uuid);
        return emailJobMapper.toDto(emailJob);
    }

    @Override
    public EmailJob getEmailJobByUuid(String uuid) {
        EmailJob emailJob = emailJobRepository.findByUuid(uuid);
        return emailJob;
    }

    @Override
    public List<EmailJobDTO> getEmailJobBySenderUuid(String senderUuid) {

        List<EmailJob> emailJobs = emailJobRepository.findBySenderUuid(senderUuid);

        return emailJobs.stream()
                .map(emailJobMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmailJobDTO> getAllEmailJobs() {
        return emailJobRepository.findAll()
                .stream()
                .map(emailJobMapper::toDto)
                .toList();
    }

    @Override
    public EmailJobDTO updateEmailJob(String uuid, EmailJobDTO emailJobDTO) {
        EmailJob existingEmailJob = emailJobRepository.findByUuid(uuid);
        if (existingEmailJob == null) {
            throw new ResourceNotFoundException("EmailJob not found with UUID: " + uuid);
        }
        if (emailJobDTO.getUuid() != null && !emailJobDTO.getUuid().equals(existingEmailJob.getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'uuid' field cannot be updated.");
        }

        if (emailJobDTO.getCreatedOn() != null && !emailJobDTO.getCreatedOn().equals(existingEmailJob.getCreatedOn())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'createdOn' field cannot be updated.");
        }

        emailJobMapper.updateFromDto(emailJobDTO, existingEmailJob);
        EmailJob updatedEmailJob = emailJobRepository.save(existingEmailJob);
        return emailJobMapper.toDto(updatedEmailJob);
    }



    @Override
    public void deleteEmailJob(String uuid) {
        EmailJob emailJob = emailJobRepository.findByUuid(uuid);
        if (emailJob==null)
        {
            throw new ResourceNotFoundException("EmailJob not found with uuid: " + uuid);
        }
        emailJobRepository.delete(emailJob);
    }

    @Override
    public List<EmailJob> getActiveEmailJobs(LocalDateTime currentDate, FrequencyEnum frequency) {
        List<EmailJob> emailJobs = emailJobRepository.findActiveMailJobs(currentDate, frequency);

        return emailJobs;
    }

    @Override
    public void saveOccurrence(EmailJob emailJob, StatusEnum status, String errorDescription) {
        Occurrence occurrence = new Occurrence();
        occurrence.setEmailJob(emailJob);
        occurrence.setStatus(status);
        occurrence.setErrorDescription(errorDescription);
        occurrenceRepository.save(occurrence);
    }
}

