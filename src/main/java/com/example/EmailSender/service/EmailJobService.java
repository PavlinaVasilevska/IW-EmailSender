package com.example.EmailSender.service;
import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.dto.EmailJobDTO;
import com.example.EmailSender.enumeration.RepetitionEnum;
import com.example.EmailSender.enumeration.StatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmailJobService {
    EmailJobDTO createEmailJob(EmailJobDTO emailJobDTO);

    Optional<EmailJobDTO> getEmailJobByUuid(String uuid);

    Optional<EmailJobDTO> getEmailJobBySenderUuid(String senderUuid);

    List<EmailJobDTO> getAllEmailJobs();

    EmailJobDTO updateEmailJob(String uuid, EmailJobDTO emailJobDTO);

    void deleteEmailJob(String uuid);

    List<EmailJob> getActiveEmailJobs(LocalDateTime currentDate, RepetitionEnum frequency);

    void recordOccurrence(EmailJob emailJob, StatusEnum status, String errorDescription);

//    void notifyAdmin(EmailJob emailJob);

    //void processEmailJobs(LocalDate currentDate, RepetitionEnum frequency);
}
