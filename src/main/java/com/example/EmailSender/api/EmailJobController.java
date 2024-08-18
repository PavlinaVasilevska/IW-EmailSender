package com.example.EmailSender.api;
import com.example.EmailSender.dto.EmailJobDTO;
import com.example.EmailSender.infrastructure.EndPoints;
import com.example.EmailSender.repository.EmailTemplateRepository;
import com.example.EmailSender.repository.UserRepository;
import com.example.EmailSender.service.EmailJobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EndPoints.EMAIL_JOBS)
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
    public Optional<ResponseEntity<EmailJobDTO>> getEmailJobByUuid(@PathVariable String uuid) {
        return emailJobService.getEmailJobByUuid(uuid).map(ResponseEntity::ok);

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
    public ResponseEntity<Optional<EmailJobDTO>> getEmailJobBySender(@PathVariable String senderUuid) {
        Optional<EmailJobDTO> emailJobDTO = emailJobService.getEmailJobBySenderUuid(senderUuid);
        return ResponseEntity.ok(emailJobDTO);
    }


}
