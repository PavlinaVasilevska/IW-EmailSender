package com.example.EmailSender.api;
import com.example.EmailSender.dto.EmailJobDTO;
import com.example.EmailSender.infrastructure.EndPoints;
import com.example.EmailSender.service.EmailJobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(EndPoints.EMAIL_JOBS)
@CrossOrigin(origins = "http://localhost:4200")
public class EmailJobController {

    private final EmailJobService emailJobService;

    public EmailJobController(EmailJobService emailJobService) {
        this.emailJobService = emailJobService;
    }


    @PostMapping
    public ResponseEntity<EmailJobDTO> createEmailJob(@RequestBody @Valid EmailJobDTO emailJobDTO) {
        EmailJobDTO createdEmailJob = emailJobService.createEmailJob(emailJobDTO);
        return ResponseEntity.status(201).body(createdEmailJob);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EmailJobDTO> getEmailJobByUuid(@PathVariable String uuid) {
        EmailJobDTO emailJob = emailJobService.getEmailJobDtoByUuid(uuid);
        return ResponseEntity.ok(emailJob);

    }


    @GetMapping
    public ResponseEntity<List<EmailJobDTO>> getAllEmailJobs() {
        List<EmailJobDTO> emailJobs = emailJobService.getAllEmailJobs();
        return ResponseEntity.ok(emailJobs);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<EmailJobDTO> updateEmailJob(@PathVariable String uuid, @RequestBody @Valid EmailJobDTO emailJobDTO) {
        EmailJobDTO updatedEmailJob = emailJobService.updateEmailJob(uuid, emailJobDTO);
        return ResponseEntity.ok(updatedEmailJob);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteEmailJob(@PathVariable String uuid) {
        emailJobService.deleteEmailJob(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sender/{senderUuid}")
    public ResponseEntity<List<EmailJobDTO>> getEmailJobBySender(@PathVariable String senderUuid) {
        List<EmailJobDTO> emailJobs = emailJobService.getEmailJobBySenderUuid(senderUuid);
        return ResponseEntity.ok(emailJobs);
    }
}

