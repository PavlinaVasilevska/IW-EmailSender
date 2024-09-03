package com.example.EmailSender.service.impl;
import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.dto.OccurrenceDTO;
import com.example.EmailSender.enumeration.FrequencyEnum;
import com.example.EmailSender.enumeration.StatusEnum;
import com.example.EmailSender.service.EmailJobService;
import com.example.EmailSender.service.EmailService;
import com.example.EmailSender.service.OccurrenceService;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailJobService emailJobService;
    private final OccurrenceService occurrenceService;

    public EmailServiceImpl(JavaMailSender javaMailSender, EmailJobService emailJobService, OccurrenceService occurrenceService) {
        this.javaMailSender = javaMailSender;
        this.emailJobService = emailJobService;
        this.occurrenceService = occurrenceService;
    }


    @Override
    public void sendEmails(FrequencyEnum frequency) {

        List<EmailJob> activeEmailJobs = emailJobService.getActiveEmailJobs(LocalDateTime.now(), frequency);
        for (EmailJob emailJob : activeEmailJobs) {
            List<OccurrenceDTO> occurrences = occurrenceService.getOccurrencesForEmailJob(emailJob.getUuid(), dateFrom(frequency), dateTo(frequency));
            if (occurrences.isEmpty()) {
                sendEmail(emailJob);
            }
        }
    }

    @Retryable(
            value = {MailSendException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    @Override
    public void sendEmail(EmailJob emailJob) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailJob.getReceivers().split(","));
            message.setSubject(emailJob.getEmailTemplate().getSubject());
            message.setText(emailJob.getEmailTemplate().getBody());
            javaMailSender.send(message);
            emailJobService.saveOccurrence(emailJob, StatusEnum.SUCCESSFUL, null);
        } catch (MailSendException e) {
            recover(e, emailJob);
            throw e;
        }
    }

    @Recover
    public void recover(Exception e, EmailJob emailJob) {
        notifyAdmin(emailJob, e.getMessage());
        emailJobService.saveOccurrence(emailJob, StatusEnum.FAILED, e.getMessage());
    }

    private void notifyAdmin(EmailJob emailJob, String errorMessage) {
        SimpleMailMessage adminMessage = new SimpleMailMessage();
        adminMessage.setTo("pavlinavasilevskakm@gmail.com");
        adminMessage.setSubject("Mail Job Failure Notification");
        adminMessage.setText("The mail job with UUID " + emailJob.getUuid() + " has failed after multiple retries. Error: " + errorMessage);

        javaMailSender.send(adminMessage);
    }

    private LocalDateTime dateFrom(FrequencyEnum frequencyEnum) {

        return switch (frequencyEnum) {
            case DAILY -> LocalDate.now().atStartOfDay();
            case WEEKLY -> LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
            case MONTHLY -> LocalDate.now().withDayOfMonth(1).atStartOfDay();
            case YEARLY -> LocalDate.now().withDayOfYear(1).atStartOfDay();
        };
    }

    private LocalDateTime dateTo(FrequencyEnum frequencyEnum) {
        return switch (frequencyEnum) {
            case DAILY -> LocalDate.now().atTime(LocalTime.MAX);
            case WEEKLY -> LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).atTime(LocalTime.MAX);
            case MONTHLY -> LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);
            case YEARLY -> LocalDate.now().withMonth(Month.DECEMBER.getValue()).withDayOfMonth(31).atTime(LocalTime.MAX);
        };
    }

}
