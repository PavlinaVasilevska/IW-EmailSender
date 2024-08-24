package com.example.EmailSender.scheduler;

import com.example.EmailSender.enumeration.RepetitionEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EmailSchedulerService {
    void performScheduledTasks();

//    void processAndSendEmails(LocalDateTime currentDate, RepetitionEnum frequency);
}