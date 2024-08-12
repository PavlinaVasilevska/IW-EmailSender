package com.example.EmailSender.repository;
import com.example.EmailSender.domain.Repetition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RepetitionRepository extends JpaRepository<Repetition, Long> {
    List<Repetition> findByFrequency(String frequency);
    List<Repetition> findByNumberOfTries(Integer numberOfTries);
    Optional<Repetition> findByUuid(String uuid);

    boolean existsByUuid(String uuid);
}
