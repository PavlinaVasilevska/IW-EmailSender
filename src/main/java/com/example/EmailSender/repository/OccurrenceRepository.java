package com.example.EmailSender.repository;
import com.example.EmailSender.domain.Occurrence;
import com.example.EmailSender.enumeration.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

 Occurrence findByUuid(String uuid);

 @Query("SELECT o FROM Occurrence o WHERE o.status = :status")
 List<Occurrence> findByStatus(@Param("status") StatusEnum status);

 void deleteByUuid(String uuid);

 @Query("SELECT o FROM Occurrence o WHERE o.emailJob.uuid = :emailJobUuid")
 List<Occurrence> findByEmailJobUuid(@Param("emailJobUuid") String emailJobUuid);

 @Query("SELECT o FROM Occurrence o WHERE o.emailJob.uuid = :emailJobUuid AND o.createdOn >= :dateFrom AND o.createdOn <= :dateTo ORDER BY o.createdOn DESC")
 List<Occurrence> findOccurrencesForEmailJob(
         @Param("emailJobUuid") String emailJobUuid,
         @Param("dateFrom") LocalDateTime dateFrom,
         @Param("dateTo") LocalDateTime dateTo);

 @Query("SELECT o FROM Occurrence o WHERE o.errorDescription = :errorDescription")
 List<Occurrence> findByErrorDescription(@Param("errorDescription") String errorDescription);
}
