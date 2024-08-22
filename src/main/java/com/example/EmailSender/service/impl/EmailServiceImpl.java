package com.example.EmailSender.service.impl;
import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.enumeration.RepetitionEnum;
import com.example.EmailSender.enumeration.StatusEnum;
import com.example.EmailSender.service.EmailJobService;
import com.example.EmailSender.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailJobService emailJobService;

    public EmailServiceImpl(JavaMailSender javaMailSender, EmailJobService emailJobService) {
        this.javaMailSender = javaMailSender;
        this.emailJobService = emailJobService;
    }


    @Override
    public void sendEmails(LocalDateTime currentDate, RepetitionEnum frequency) {
        List<EmailJob> activeEmailJobs = emailJobService.getActiveEmailJobs(currentDate, frequency);

        for (EmailJob emailJob : activeEmailJobs) {
            try {
                // Attempt to send email
                sendEmail(emailJob);
            } catch (Exception e) {
                // Handle exception if retries are exhausted
                recover(e, emailJob);
            }
        }
    }
    @Retryable(value = { MessagingException.class }, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    @Override
    public void sendEmail(EmailJob emailJob) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailJob.getReceivers().split(","));
        message.setSubject(emailJob.getEmailTemplate().getSubject());
        message.setText(emailJob.getEmailTemplate().getBody());

        javaMailSender.send(message);

    }
    @Recover
    public void recover(Exception e, EmailJob emailJob) {
        // Notify admin after retries are exhausted
        notifyAdmin(emailJob, e.getMessage());
        // Log the error and update the status
        emailJobService.recordOccurrence(emailJob, StatusEnum.FAILED, e.getMessage());
    }

    private void notifyAdmin(EmailJob emailJob, String errorMessage) {
        SimpleMailMessage adminMessage = new SimpleMailMessage();
        adminMessage.setTo("pavlinavasilevskakm@gmail.com");
        adminMessage.setSubject("Mail Job Failure Notification");
        adminMessage.setText("The mail job with UUID " + emailJob.getUuid() + " has failed after multiple retries. Error: " + errorMessage);

        javaMailSender.send(adminMessage);
    }

}