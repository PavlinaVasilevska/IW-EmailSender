package com.example.EmailSender.service;
import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.enumeration.RepetitionEnum;
public interface EmailService {
    void sendEmails(RepetitionEnum frequency);

    void sendEmail(EmailJob emailJob);
}