package com.example.EmailSender.repository;

import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.domain.EmailTemplate;
import com.example.EmailSender.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailJobRepository extends JpaRepository<EmailJob, Long> {
    Optional<EmailJob> findByUuid(String uuid);
    Optional<EmailJob> findBySender(User sender);
    Optional<EmailJob> findByEmailTemplate(EmailTemplate emailTemplate);

}
