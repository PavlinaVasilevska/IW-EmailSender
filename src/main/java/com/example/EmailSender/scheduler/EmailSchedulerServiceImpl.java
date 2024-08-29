package com.example.EmailSender.scheduler;
import com.example.EmailSender.enumeration.FrequencyEnum;
import com.example.EmailSender.service.EmailJobService;
import com.example.EmailSender.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class EmailSchedulerServiceImpl implements EmailSchedulerService {

    private final EmailJobService emailJobService;
    private final EmailService emailService;

    public EmailSchedulerServiceImpl(EmailJobService emailJobService, EmailService emailService) {
        this.emailJobService = emailJobService;
        this.emailService = emailService;
    }
    @Scheduled(cron = "0 * * * * *")
    @Override
    public void performScheduledTasks() {
        for (FrequencyEnum frequency : FrequencyEnum.values()) {
            emailService.sendEmails(frequency);
        }
    }


}
