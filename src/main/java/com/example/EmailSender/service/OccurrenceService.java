package com.example.EmailSender.service;

import com.example.EmailSender.dto.OccurrenceDTO;
import com.example.EmailSender.enumeration.StatusEnum;

import java.util.List;
import java.util.Optional;

public interface OccurrenceService {
    OccurrenceDTO createOccurrence(OccurrenceDTO occurrenceDTO);

    Optional<OccurrenceDTO> getOccurrenceByUuid(String uuid);

    List<OccurrenceDTO> getOccurrencesByStatus(StatusEnum status);

    List<OccurrenceDTO> getAllOccurrences();

    List<OccurrenceDTO> getOccurrencesByEmailJobUuid(String emailJobUuid);

    OccurrenceDTO updateOccurrence(String uuid, OccurrenceDTO occurrenceDTO);

    void deleteOccurrence(String uuid);

    List<OccurrenceDTO> getOccurrencesByErrorDescription(String errorDescription);
}
