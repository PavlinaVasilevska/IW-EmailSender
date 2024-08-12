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
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailTemplateMapper emailTemplateMapper;

    @Override
    public EmailTemplateDTO createEmailTemplate(EmailTemplateDTO emailTemplateDTO) {
        EmailTemplate emailTemplate = emailTemplateMapper.emailTemplateDTOToEmailTemplate(emailTemplateDTO);
        emailTemplate = emailTemplateRepository.save(emailTemplate);
        return emailTemplateMapper.emailTemplateToEmailTemplateDTO(emailTemplate);
    }

    @Override
    public Optional<EmailTemplateDTO> getEmailTemplateByUuid(String uuid) {
        return emailTemplateRepository.findByUuid(uuid)
                .map(emailTemplateMapper::emailTemplateToEmailTemplateDTO);
    }

    @Override
    public Optional<EmailTemplateDTO> getEmailTemplateBySubject(String subject) {
        return emailTemplateRepository.findBySubject(subject)
                .map(emailTemplateMapper::emailTemplateToEmailTemplateDTO);
    }

    @Override
    public List<EmailTemplateDTO> getEmailTemplatesByBodyContaining(String keyword) {
        List<EmailTemplate> emailTemplates = emailTemplateRepository.findByBodyContaining(keyword);
        return emailTemplateMapper.emailTemplatesToEmailTemplateDTOs(emailTemplates);
    }

    @Override
    public Optional<EmailTemplateDTO> getEmailTemplateByBody(String body) {
        return emailTemplateRepository.findByBody(body)
                .map(emailTemplateMapper::emailTemplateToEmailTemplateDTO);
    }

    @Override
    public List<EmailTemplateDTO> getAllEmailTemplates() {
        List<EmailTemplate> emailTemplates = emailTemplateRepository.findAll();
        return emailTemplateMapper.emailTemplatesToEmailTemplateDTOs(emailTemplates);
    }

    @Override
    public EmailTemplateDTO updateEmailTemplate(String uuid, EmailTemplateDTO emailTemplateDTO) {
        // Fetch the EmailTemplate using the UUID
        EmailTemplate emailTemplate = emailTemplateRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("EmailTemplate not found"));

        // Update the fields of the EmailTemplate
        emailTemplate.setSubject(emailTemplateDTO.getSubject());
        emailTemplate.setBody(emailTemplateDTO.getBody());
        // Update other fields if needed

        // Save the updated EmailTemplate
        emailTemplate = emailTemplateRepository.save(emailTemplate);

        // Convert the updated EmailTemplate to DTO and return
        return emailTemplateMapper.emailTemplateToEmailTemplateDTO(emailTemplate);
    }

    @Override
    public void deleteEmailTemplate(String uuid) {
        if (!emailTemplateRepository.existsByUuid(uuid)) {
            throw new ResourceNotFoundException("EmailTemplate not found with UUID: " + uuid);
        }
        emailTemplateRepository.deleteByUuid(uuid);
    }
}
