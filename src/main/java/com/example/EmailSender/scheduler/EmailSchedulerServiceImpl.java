package com.example.EmailSender.scheduler;
import com.example.EmailSender.enumeration.RepetitionEnum;
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
        for (RepetitionEnum frequency : RepetitionEnum.values()) {
            emailService.sendEmails(frequency);
        }
    }
//
//    @Override
//    public void processAndSendEmails(LocalDateTime currentDate, RepetitionEnum frequency) {
//
//    }


}
