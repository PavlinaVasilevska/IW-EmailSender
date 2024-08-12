package com.example.EmailSender.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MyScheduledService {

    @Scheduled(fixedDelay = 30000) // 30 seconds in milliseconds
    public void performTask() {
        // Your task logic here
        System.out.println("Scheduled task executed at: " + LocalDateTime.now());
    }
}