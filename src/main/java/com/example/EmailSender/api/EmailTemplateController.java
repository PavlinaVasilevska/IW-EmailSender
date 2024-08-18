package com.example.EmailSender.api;

import com.example.EmailSender.dto.EmailTemplateDTO;
import com.example.EmailSender.infrastructure.EndPoints;
import com.example.EmailSender.service.EmailTemplateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EndPoints.EMAIL_TEMPLATES)

public class EmailTemplateController {

    private final EmailTemplateService emailTemplateService;

    public EmailTemplateController(EmailTemplateService emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }

    @PostMapping
    public ResponseEntity<EmailTemplateDTO> createEmailTemplate(@Valid @RequestBody EmailTemplateDTO emailTemplateDTO) {
        EmailTemplateDTO createdEmailTemplate = emailTemplateService.createEmailTemplate(emailTemplateDTO);
        return new ResponseEntity<>(createdEmailTemplate, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public Optional<ResponseEntity<EmailTemplateDTO>> getEmailTemplateByUuid(@PathVariable String uuid) {
        return emailTemplateService.getEmailTemplateByUuid(uuid).map(ResponseEntity::ok);

    }
    @GetMapping("/subject/{subject}")
    public ResponseEntity<EmailTemplateDTO> getEmailTemplateBySubject(@PathVariable String subject) {
        Optional<EmailTemplateDTO> emailTemplateDTO = emailTemplateService.getEmailTemplateBySubject(subject);
        return emailTemplateDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/body/contains/{keyword}")
    public ResponseEntity<List<EmailTemplateDTO>> getEmailTemplatesByBodyContaining(@PathVariable String keyword) {
        List<EmailTemplateDTO> emailTemplates = emailTemplateService.getEmailTemplatesByBodyContaining(keyword);
        return ResponseEntity.ok(emailTemplates);
    }

    @GetMapping("/body/{body}")
    public ResponseEntity<EmailTemplateDTO> getEmailTemplateByBody(@PathVariable String body) {
        Optional<EmailTemplateDTO> emailTemplateDTO = emailTemplateService.getEmailTemplateByBody(body);
        return emailTemplateDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EmailTemplateDTO>> getAllEmailTemplates() {
        List<EmailTemplateDTO> emailTemplates = emailTemplateService.getAllEmailTemplates();
        return ResponseEntity.ok(emailTemplates);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<EmailTemplateDTO> updateEmailTemplate(@PathVariable String uuid, @Valid @RequestBody EmailTemplateDTO emailTemplateDTO) {
        EmailTemplateDTO updatedEmailTemplate = emailTemplateService.updateEmailTemplate(uuid, emailTemplateDTO);
        return ResponseEntity.ok(updatedEmailTemplate);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteEmailTemplate(@PathVariable String uuid) {
        emailTemplateService.deleteEmailTemplate(uuid);
        return ResponseEntity.noContent().build();
    }
}
