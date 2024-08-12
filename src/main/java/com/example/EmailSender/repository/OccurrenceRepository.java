package com.example.EmailSender.repository;
import com.example.EmailSender.domain.Occurrence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {

    Optional<Occurrence> findByUuid(String uuid);
    Optional<Occurrence>findByEmailJobUuid(String emailJobUuid);
    List<Occurrence> findByStatus(Integer status);
    void deleteByUuid(String  uuid);

}
