package com.example.EmailSender.api;
import com.example.EmailSender.domain.EmailTemplate;
import com.example.EmailSender.domain.User;
import com.example.EmailSender.dto.EmailJobDTO;
import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.repository.EmailTemplateRepository;
import com.example.EmailSender.repository.UserRepository;
import com.example.EmailSender.service.EmailJobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/emailjobs")
public class EmailJobController {

    private final EmailJobService emailJobService;
    private final UserRepository userRepository;
    private final EmailTemplateRepository emailTemplateRepository;

    public EmailJobController(EmailJobService emailJobService, UserRepository userRepository, EmailTemplateRepository emailTemplateRepository) {
        this.emailJobService = emailJobService;
        this.userRepository = userRepository;
        this.emailTemplateRepository = emailTemplateRepository;
    }

    @PostMapping
    public ResponseEntity<EmailJobDTO> createEmailJob(@RequestBody EmailJobDTO emailJobDTO) {
        EmailJobDTO createdEmailJob = emailJobService.createEmailJob(emailJobDTO);
        return new ResponseEntity<>(createdEmailJob, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public EmailJobDTO getEmailJobByUuid(@PathVariable String uuid) {
        EmailJobDTO emailJobDTO = emailJobService.getEmailJobByUuid(uuid);
        return emailJobDTO;
    }

    @GetMapping
    public ResponseEntity<List<EmailJobDTO>> getAllEmailJobs() {
        List<EmailJobDTO> emailJobs = emailJobService.getAllEmailJobs();
        return ResponseEntity.ok(emailJobs);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<EmailJobDTO> updateEmailJob(@PathVariable String uuid, @RequestBody EmailJobDTO emailJobDTO) {
        EmailJobDTO updatedEmailJob = emailJobService.updateEmailJob(uuid, emailJobDTO);
        return ResponseEntity.ok(updatedEmailJob);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteEmailJob(@PathVariable String uuid) {
        emailJobService.deleteEmailJob(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sender/{senderUuid}")
    public ResponseEntity<EmailJobDTO> getEmailJobBySender(@PathVariable String senderUuid) {
        // Fetch User by UUID
        User sender = userRepository.findByUuid(senderUuid)
                .orElseThrow(() -> new ResourceNotFoundException("User with UUID " + senderUuid + " not found"));

        // Fetch EmailJob by sender
        Optional<EmailJobDTO> emailJobDTO = emailJobService.getEmailJobBySender(sender);

        // Return 404 Not Found if no EmailJob is found
        return emailJobDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-email-template/{emailTemplateUuid}")
    public ResponseEntity<EmailJobDTO> getEmailJobByEmailTemplate(@PathVariable String emailTemplateUuid) {
        EmailTemplate emailTemplate = emailTemplateRepository.findByUuid(emailTemplateUuid)
                .orElseThrow(() -> new ResourceNotFoundException("EmailTemplate not found"));

        Optional<EmailJobDTO> emailJobDTO = emailJobService.getEmailJobByEmailTemplate(emailTemplate);

        if (emailJobDTO.isPresent()) {
            return ResponseEntity.ok(emailJobDTO.get());
        } else {
            throw new ResourceNotFoundException("EmailJob not found for the given EmailTemplate");
        }
    }

}
