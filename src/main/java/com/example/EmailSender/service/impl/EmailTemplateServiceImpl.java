package com.example.EmailSender.service.impl;

import com.example.EmailSender.domain.EmailTemplate;
import com.example.EmailSender.dto.EmailTemplateDTO;
import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.mapper.EmailTemplateMapper;
import com.example.EmailSender.repository.EmailTemplateRepository;
import com.example.EmailSender.service.EmailTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailTemplateMapper emailTemplateMapper;

    @Override
    public EmailTemplateDTO createEmailTemplate(EmailTemplateDTO emailTemplateDTO) {
        EmailTemplate emailTemplate = emailTemplateMapper.toEntity(emailTemplateDTO);
        emailTemplate = emailTemplateRepository.save(emailTemplate);
        return emailTemplateMapper.toDto(emailTemplate);
    }

    @Override
    public EmailTemplate getTemplateByUuid(String uuid) {
        EmailTemplate emailTemplate = emailTemplateRepository.findByUuid(uuid);
        if (emailTemplate == null) {
            throw new ResourceNotFoundException("Email template with uuid " + uuid + " not found");
        }
        return emailTemplate;
    }

    @Override
    public EmailTemplateDTO getEmailTemplateByUuid(String uuid) {
        EmailTemplate emailTemplate = emailTemplateRepository.findByUuid(uuid);
        if (emailTemplate == null) {
            throw new ResourceNotFoundException("Email template with uuid " + uuid + " not found");
        }
        return emailTemplateMapper.toDto(emailTemplate);
    }

    @Override
    public EmailTemplateDTO getEmailTemplateBySubject(String subject) {
        EmailTemplate emailTemplate = emailTemplateRepository.findBySubject(subject);
        if (emailTemplate == null) {
            throw new ResourceNotFoundException("Email template with subject " + subject + " not found");
        }
        return emailTemplateMapper.toDto(emailTemplate);
    }

    @Override
    public List<EmailTemplateDTO> getEmailTemplatesByBodyContaining(String keyword) {
        List<EmailTemplate> emailTemplates = emailTemplateRepository.findByBodyContaining(keyword);

        if (emailTemplates.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No email templates found containing the keyword '%s'", keyword));
        }

        return emailTemplateMapper.toDtoList(emailTemplates);
    }

    @Override
    public EmailTemplateDTO getEmailTemplateByBody(String body) {
        EmailTemplate emailTemplate = emailTemplateRepository.findByBody(body);
        if (emailTemplate == null) {
            throw new ResourceNotFoundException(String.format("No email template found with body '%s'", body));
        }
        return emailTemplateMapper.toDto(emailTemplate);
    }

    @Override
    public List<EmailTemplateDTO> getAllEmailTemplates() {
        List<EmailTemplate> emailTemplates = emailTemplateRepository.findAll();
        return emailTemplateMapper.toDtoList(emailTemplates);
    }

    @Override
    public EmailTemplateDTO updateEmailTemplate(String uuid, EmailTemplateDTO emailTemplateDTO) {
        EmailTemplate emailTemplate = emailTemplateRepository.findByUuid(uuid);
        if (emailTemplate == null) {
            throw new ResourceNotFoundException("Email template with uuid " + uuid + " not found");
        }

        emailTemplate.setSubject(emailTemplateDTO.getSubject());
        emailTemplate.setBody(emailTemplateDTO.getBody());

        emailTemplate = emailTemplateRepository.save(emailTemplate);
        return emailTemplateMapper.toDto(emailTemplate);
    }

    @Override
    public void deleteEmailTemplate(String uuid) {
        if (!emailTemplateRepository.existsByUuid(uuid)) {
            throw new ResourceNotFoundException("EmailTemplate not found with UUID: " + uuid);
        }
        emailTemplateRepository.deleteByUuid(uuid);
    }
}
