package com.example.EmailSender.service;

import com.example.EmailSender.dto.EmailTemplateDTO;

import java.util.List;
import java.util.Optional;

public interface EmailTemplateService {
    EmailTemplateDTO createEmailTemplate(EmailTemplateDTO emailTemplateDTO);

    Optional<EmailTemplateDTO> getEmailTemplateByUuid(String uuid);

    Optional<EmailTemplateDTO> getEmailTemplateBySubject(String subject);

    List<EmailTemplateDTO> getEmailTemplatesByBodyContaining(String keyword);

    Optional<EmailTemplateDTO> getEmailTemplateByBody(String body);

    List<EmailTemplateDTO> getAllEmailTemplates();

    EmailTemplateDTO updateEmailTemplate(String uuid, EmailTemplateDTO emailTemplateDTO);

    void deleteEmailTemplate(String uuid);
}
