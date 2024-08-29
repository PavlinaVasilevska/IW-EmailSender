package com.example.EmailSender.api;
import com.example.EmailSender.dto.EmailTemplateDTO;
import com.example.EmailSender.infrastructure.EndPoints;
import com.example.EmailSender.service.EmailTemplateService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
        return ResponseEntity.status(201).body(createdEmailTemplate);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EmailTemplateDTO> getEmailTemplateByUuid(@PathVariable String uuid) {
        EmailTemplateDTO emailTemplate = emailTemplateService.getEmailTemplateByUuid(uuid);
        return ResponseEntity.ok(emailTemplate);

    }
    @GetMapping("/subject/{subject}")
    public ResponseEntity<EmailTemplateDTO> getEmailTemplateBySubject(@PathVariable String subject) {
        EmailTemplateDTO emailTemplate = emailTemplateService.getEmailTemplateBySubject(subject);
        return ResponseEntity.ok(emailTemplate);
    }

    @GetMapping("/body/contains/{keyword}")
    public ResponseEntity<List<EmailTemplateDTO>> getEmailTemplatesByBodyContaining(@PathVariable String keyword) {
        List<EmailTemplateDTO> emailTemplates = emailTemplateService.getEmailTemplatesByBodyContaining(keyword);
        return ResponseEntity.ok(emailTemplates);
    }

    @GetMapping("/body/{body}")
    public ResponseEntity<EmailTemplateDTO> getEmailTemplateByBody(@PathVariable String body) {
        EmailTemplateDTO emailTemplate= emailTemplateService.getEmailTemplateByBody(body);
        return ResponseEntity.ok(emailTemplate);
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
