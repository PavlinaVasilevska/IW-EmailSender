package com.example.EmailSender.service;
import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.enumeration.FrequencyEnum;
public interface EmailService {
    void sendEmails(FrequencyEnum frequency);

    void sendEmail(EmailJob emailJob);
}