package com.example.EmailSender.service;

import com.example.EmailSender.domain.EmailTemplate;
import com.example.EmailSender.domain.User;
import com.example.EmailSender.dto.EmailJobDTO;

import java.util.List;
import java.util.Optional;

public interface EmailJobService {
    EmailJobDTO createEmailJob(EmailJobDTO emailJobDTO);

    EmailJobDTO getEmailJobByUuid(String uuid);

    Optional<EmailJobDTO> getEmailJobBySender(User sender);

    Optional<EmailJobDTO> getEmailJobByEmailTemplate(EmailTemplate emailTemplate);

    List<EmailJobDTO> getAllEmailJobs();

    EmailJobDTO updateEmailJob(String uuid, EmailJobDTO emailJobDTO);

    void deleteEmailJob(String uuid);
}
