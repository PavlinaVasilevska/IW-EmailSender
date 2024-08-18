package com.example.EmailSender.service.impl;

import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.domain.EmailTemplate;
import com.example.EmailSender.domain.Repetition;
import com.example.EmailSender.domain.User;
import com.example.EmailSender.dto.EmailJobDTO;
import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.mapper.EmailJobMapper;
import com.example.EmailSender.mapper.EmailTemplateMapper;
import com.example.EmailSender.mapper.RepetitionMapper;
import com.example.EmailSender.mapper.UserMapper;
import com.example.EmailSender.repository.EmailJobRepository;
import com.example.EmailSender.repository.EmailTemplateRepository;
import com.example.EmailSender.repository.RepetitionRepository;
import com.example.EmailSender.repository.UserRepository;
import com.example.EmailSender.service.EmailJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailJobServiceImpl implements EmailJobService {

    private final EmailJobRepository emailJobRepository;
    private final EmailJobMapper emailJobMapper;
    private final EmailTemplateMapper emailTemplateMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RepetitionMapper repetitionMapper;
    private final EmailTemplateRepository emailTemplateRepository;
    private final RepetitionRepository repetitionRepository;

    @Override
    public EmailJobDTO createEmailJob(EmailJobDTO emailJobDTO) {
        // Convert EmailJobDTO to EmailJob entity
        EmailJob emailJob = emailJobMapper.toEntity(emailJobDTO);

        // Fetch and set EmailTemplate using the UUID
        EmailTemplate emailTemplate = emailTemplateRepository.findByUuid(emailJobDTO.getEmailTemplate().getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("EmailTemplate not found"));
        emailJob.setEmailTemplate(emailTemplate);

        // Fetch and set Sender using the UUID
        User sender = userRepository.findByUuid(emailJobDTO.getSender().getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        emailJob.setSender(sender);

        // Fetch and set Repetition using the UUID
        Repetition repetition = repetitionRepository.findByUuid(emailJobDTO.getRepetition().getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Repetition not found"));
        emailJob.setRepetition(repetition);

        // Handle the recurrence pattern and other properties
        emailJob.setStartDate(emailJobDTO.getStartDate());
        emailJob.setEndDate(emailJobDTO.getEndDate());
        emailJob.setEnabled(emailJobDTO.getEnabled());
        emailJob.setReceivers(emailJobDTO.getReceivers());

        // Save the EmailJob entity
        EmailJob savedEmailJob = emailJobRepository.save(emailJob);

        // Convert saved EmailJob to EmailJobDTO and return
        return emailJobMapper.toDto(savedEmailJob);
    }

    @Override
    public Optional<EmailJobDTO> getEmailJobByUuid(String uuid) {
      EmailJob emailJob=emailJobRepository.findByUuid(uuid)
              .orElseThrow(()-> new ResourceNotFoundException("EmailJob with UUID " + uuid + " not found"));
      return Optional.of(emailJobMapper.toDto(emailJob));
    }

    @Override
    public Optional<EmailJobDTO> getEmailJobBySenderUuid(String senderUuid) {
        // Fetch User by UUID
        User user = userRepository.findByUuid(senderUuid)
                .orElseThrow(() -> new ResourceNotFoundException("User with UUID " + senderUuid + " not found"));

        // Fetch EmailJob by the found User
        EmailJob emailJob = emailJobRepository.findBySender(user)
                .orElseThrow(() -> new ResourceNotFoundException("EmailJob for sender with UUID " + senderUuid + " not found"));

        // Map EmailJob to EmailJobDTO and return
        return Optional.ofNullable(emailJobMapper.toDto(emailJob));
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
        EmailJob emailJob = emailJobRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("EmailJob with UUID " + uuid + " not found"));
        emailJobMapper.updateFromDto(emailJobDTO, emailJob);
        EmailJob updatedEmailJob = emailJobRepository.save(emailJob);
        return emailJobMapper.toDto(updatedEmailJob);
    }

    @Override
    public void deleteEmailJob(String uuid) {
        EmailJob emailJob = emailJobRepository.findByUuid(uuid).orElseThrow(() -> new RuntimeException("EmailJob not found"));
        emailJobRepository.delete(emailJob);
    }
}
