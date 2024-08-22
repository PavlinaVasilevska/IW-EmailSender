package com.example.EmailSender.service;
import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.enumeration.RepetitionEnum;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EmailService {
    void sendEmails(LocalDateTime currentDate, RepetitionEnum frequency);

    void sendEmail(EmailJob emailJob);
}