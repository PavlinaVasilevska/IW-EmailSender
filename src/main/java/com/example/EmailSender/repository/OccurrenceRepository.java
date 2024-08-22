package com.example.EmailSender.repository;
import com.example.EmailSender.domain.Occurrence;
import com.example.EmailSender.enumeration.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

    Optional<Occurrence> findByUuid(String uuid);

    @Query("SELECT o FROM Occurrence o WHERE o.status = :status")
    List<Occurrence> findByStatus(@Param("status") StatusEnum status);

    void deleteByUuid(String uuid);

    List<Occurrence> findByEmailJobUuid(String emailJobUuid);

    // Find occurrences by error description
    @Query("SELECT o FROM Occurrence o WHERE o.errorDescription = :errorDescription")
    List<Occurrence> findByErrorDescription(@Param("errorDescription") String errorDescription);
}
