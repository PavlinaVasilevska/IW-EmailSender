package com.example.EmailSender.scheduler;

import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.enumeration.RepetitionEnum;
import com.example.EmailSender.enumeration.StatusEnum;
import com.example.EmailSender.service.EmailJobService;
import com.example.EmailSender.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailSchedulerServiceImpl implements EmailSchedulerService {

    private final EmailJobService emailJobService;
    private final EmailService emailService;

    public EmailSchedulerServiceImpl(EmailJobService emailJobService, EmailService emailService) {
        this.emailJobService = emailJobService;
        this.emailService = emailService;
    }

//    @Scheduled(cron = "0 0 10 * * *") // Every day at 10 AM
    @Override
    public void performScheduledTasks() {
        LocalDateTime currentDate = LocalDate.now().atStartOfDay();
        for (RepetitionEnum frequency : RepetitionEnum.values()) {
            processAndSendEmails(currentDate, frequency);
        }
    }

    @Override
    public void processAndSendEmails(LocalDateTime currentDate, RepetitionEnum frequency) {
        List<EmailJob> emailJobs = emailJobService.getActiveEmailJobs(currentDate, frequency);

        for (EmailJob emailJob : emailJobs) {
            try {
                emailService.sendEmail(emailJob);
                emailJobService.recordOccurrence(emailJob, StatusEnum.SUCCESSFUL, null);
            } catch (Exception e) {
                emailJobService.recordOccurrence(emailJob, StatusEnum.FAILED, e.getMessage());
            }
        }
    }


}
