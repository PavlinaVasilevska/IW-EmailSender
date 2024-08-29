package com.example.EmailSender.repository;

import com.example.EmailSender.domain.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface EmailTemplateRepository extends JpaRepository <EmailTemplate, Long> {
    EmailTemplate findByUuid(String uuid);
    EmailTemplate findBySubject(String subject);
    List<EmailTemplate> findByBodyContaining(String keyword);
    EmailTemplate findByBody(String body);
    void deleteByUuid(String uuid);
    boolean existsByUuid(String uuid);
}
