package com.example.EmailSender.service;

import com.example.EmailSender.domain.EmailTemplate;
import com.example.EmailSender.dto.EmailTemplateDTO;

import java.util.List;

public interface EmailTemplateService {
    EmailTemplateDTO createEmailTemplate(EmailTemplateDTO emailTemplateDTO);

    EmailTemplate getTemplateByUuid(String uuid);

    EmailTemplateDTO getEmailTemplateByUuid(String uuid);

    EmailTemplateDTO getEmailTemplateBySubject(String subject);

    List<EmailTemplateDTO> getEmailTemplatesByBodyContaining(String keyword);

    EmailTemplateDTO getEmailTemplateByBody(String body);

    List<EmailTemplateDTO> getAllEmailTemplates();

    EmailTemplateDTO updateEmailTemplate(String uuid, EmailTemplateDTO emailTemplateDTO);

    void deleteEmailTemplate(String uuid);
}
