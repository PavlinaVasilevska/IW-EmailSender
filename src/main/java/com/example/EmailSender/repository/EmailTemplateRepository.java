package com.example.EmailSender.repository;

import com.example.EmailSender.domain.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface EmailTemplateRepository extends JpaRepository <EmailTemplate, Long> {
    Optional<EmailTemplate> findByUuid(String uuid);
    Optional<EmailTemplate> findBySubject(String subject);
    List<EmailTemplate> findByBodyContaining(String keyword);
    Optional<EmailTemplate> findByBody(String body);
    void deleteByUuid(String uuid);
    boolean existsByUuid(String uuid);
}
