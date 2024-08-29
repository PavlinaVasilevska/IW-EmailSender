package com.example.EmailSender.service;
import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.dto.EmailJobDTO;
import com.example.EmailSender.enumeration.FrequencyEnum;
import com.example.EmailSender.enumeration.StatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailJobService {
    EmailJobDTO createEmailJob(EmailJobDTO emailJobDTO);

    EmailJobDTO getEmailJobDtoByUuid(String uuid);

    EmailJob getEmailJobByUuid(String uuid);

    List<EmailJobDTO> getEmailJobBySenderUuid(String senderUuid);

    List<EmailJobDTO> getAllEmailJobs();

    EmailJobDTO updateEmailJob(String uuid, EmailJobDTO emailJobDTO);

    void deleteEmailJob(String uuid);

    List<EmailJob> getActiveEmailJobs(LocalDateTime currentDate, FrequencyEnum frequency);

    void saveOccurrence(EmailJob emailJob, StatusEnum status, String errorDescription);

}
