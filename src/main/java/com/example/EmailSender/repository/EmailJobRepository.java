package com.example.EmailSender.repository;

import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.domain.EmailTemplate;
import com.example.EmailSender.enumeration.FrequencyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface EmailJobRepository extends JpaRepository<EmailJob, Long> {
    EmailJob findByUuid(String uuid);
    EmailJob findByEmailTemplate(EmailTemplate emailTemplate);
    List<EmailJob> findBySenderUuid(String senderUuid);
    @Query("SELECT e FROM EmailJob e " +
            "WHERE e.enabled = true " +
            "AND (e.endDate IS NULL OR e.endDate >= :currentDate) " +
            "AND e.startDate <= :currentDate " +
            "AND e.frequency = :frequency")
    List<EmailJob> findActiveMailJobs(@Param("currentDate") LocalDateTime currentDate,
                                      @Param("frequency") FrequencyEnum frequency);

}
