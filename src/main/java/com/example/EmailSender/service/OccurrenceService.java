package com.example.EmailSender.service;

import com.example.EmailSender.dto.OccurrenceDTO;

import java.util.List;
import java.util.Optional;

public interface OccurrenceService {
    OccurrenceDTO createOccurrence(OccurrenceDTO occurrenceDTO);

    Optional<OccurrenceDTO> getOccurrenceByUuid(String uuid);

    Optional<OccurrenceDTO> getOccurrenceByEmailJobUuid(String emailJobUuid);

    List<OccurrenceDTO> getOccurrencesByStatus(Integer status);

    List<OccurrenceDTO> getAllOccurrences();

    OccurrenceDTO updateOccurrence(String uuid, OccurrenceDTO occurrenceDTO);

    void deleteOccurrence(String uuid);
}
