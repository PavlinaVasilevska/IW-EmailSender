package com.example.EmailSender.service.impl;

import com.example.EmailSender.domain.*;
import com.example.EmailSender.dto.EmailJobDTO;
import com.example.EmailSender.enumeration.RepetitionEnum;
import com.example.EmailSender.enumeration.StatusEnum;
import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.mapper.EmailJobMapper;
import com.example.EmailSender.repository.*;
import com.example.EmailSender.service.EmailJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailJobServiceImpl implements EmailJobService {

    private final EmailJobRepository emailJobRepository;
    private final EmailJobMapper emailJobMapper;
    private final UserRepository userRepository;
    private final EmailTemplateRepository emailTemplateRepository;
    private final RepetitionRepository repetitionRepository;
    private final JavaMailSender javaMailSender;
    private final OccurrenceRepository occurrenceRepository;

    @Override
    public EmailJobDTO createEmailJob(EmailJobDTO emailJobDTO) {
        EmailJob emailJob = emailJobMapper.toEntity(emailJobDTO);

        EmailTemplate emailTemplate = emailTemplateRepository.findByUuid(emailJobDTO.getEmailTemplate().getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("EmailTemplate not found"));
        emailJob.setEmailTemplate(emailTemplate);

        User sender = userRepository.findByUuid(emailJobDTO.getSender().getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        emailJob.setSender(sender);

        Repetition repetition = repetitionRepository.findByUuid(emailJobDTO.getRepetition().getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Repetition not found"));
        emailJob.setRepetition(repetition);

        emailJob.setStartDate(emailJobDTO.getStartDate());
        emailJob.setEndDate(emailJobDTO.getEndDate());
        emailJob.setEnabled(emailJobDTO.getEnabled());
        emailJob.setReceivers(emailJobDTO.getReceivers());

        EmailJob savedEmailJob = emailJobRepository.save(emailJob);

        return emailJobMapper.toDto(savedEmailJob);
    }

    @Override
    public Optional<EmailJobDTO> getEmailJobByUuid(String uuid) {
        EmailJob emailJob = emailJobRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("EmailJob with UUID " + uuid + " not found"));
        return Optional.of(emailJobMapper.toDto(emailJob));
    }

    @Override
    public Optional<EmailJobDTO> getEmailJobBySenderUuid(String senderUuid) {
        User user = userRepository.findByUuid(senderUuid)
                .orElseThrow(() -> new ResourceNotFoundException("User with UUID " + senderUuid + " not found"));

        EmailJob emailJob = emailJobRepository.findBySender(user)
                .orElseThrow(() -> new ResourceNotFoundException("EmailJob for sender with UUID " + senderUuid + " not found"));

        return Optional.of(emailJobMapper.toDto(emailJob));
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
        EmailJob emailJob = emailJobRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("EmailJob with UUID " + uuid + " not found"));

        emailJobMapper.updateFromDto(emailJobDTO, emailJob);

        EmailJob updatedEmailJob = emailJobRepository.save(emailJob);
        return emailJobMapper.toDto(updatedEmailJob);
    }

    @Override
    public void deleteEmailJob(String uuid) {
        EmailJob emailJob = emailJobRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("EmailJob not found with UUID " + uuid));
        emailJobRepository.delete(emailJob);
    }

    @Override
    public List<EmailJob> getActiveEmailJobs(LocalDateTime currentDate, RepetitionEnum frequency) {
        List<EmailJob> emailJobs = emailJobRepository.findActiveMailJobs(currentDate, frequency);

        return emailJobs.stream()
                .filter(job -> shouldSendEmail(job, currentDate))
                .collect(Collectors.toList());
    }

    private boolean shouldSendEmail(EmailJob job, LocalDateTime currentDate) {
        LocalDateTime startDate = job.getStartDate();

        switch (job.getRepetition().getFrequency()) {
            case DAILY:
                return true; // Always true for daily jobs
            case WEEKLY:
                return startDate.getDayOfWeek() == currentDate.getDayOfWeek();
            case MONTHLY:
                return startDate.getDayOfMonth() == currentDate.getDayOfMonth();
            case YEARLY:
                return startDate.getDayOfMonth() == currentDate.getDayOfMonth() &&
                        startDate.getMonth() == currentDate.getMonth();
            default:
                return false;
        }
    }

    @Override
    public void recordOccurrence(EmailJob emailJob, StatusEnum status, String errorDescription) {
        Occurrence occurrence = new Occurrence();
        occurrence.setEmailJob(emailJob);
        occurrence.setStatus(status);
        occurrence.setErrorDescription(errorDescription);
        occurrenceRepository.save(occurrence);
    }
}



//    private void handleRetry(EmailJob emailJob) {
//        Repetition repetition = emailJob.getRepetition();
//        if (repetition.getNumberOfTries() >= 3) {
//            notifyAdmin(emailJob);
//        } else {
//            repetition.setNumberOfTries(repetition.getNumberOfTries() + 1);
//            repetitionRepository.save(repetition);
//        }
//    }

//    private void resetRepetition(Repetition repetition) {
//        repetition.setNumberOfTries(0);
//        repetitionRepository.save(repetition);
//    }

//    @Override
//    public void notifyAdmin(EmailJob emailJob) {
//        SimpleMailMessage adminMessage = new SimpleMailMessage();
//        adminMessage.setTo("admin@example.com");
//        adminMessage.setSubject("Mail Job Failure Notification");
//        adminMessage.setText("The mail job with UUID " + emailJob.getUuid() + " has failed after multiple retries.");
//
//        javaMailSender.send(adminMessage);
//    }
