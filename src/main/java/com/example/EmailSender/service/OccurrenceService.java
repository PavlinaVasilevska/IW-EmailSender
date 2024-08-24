package com.example.EmailSender.service;
import com.example.EmailSender.dto.OccurrenceDTO;
import com.example.EmailSender.enumeration.StatusEnum;
import java.time.LocalDateTime;
import java.util.List;

public interface OccurrenceService {
    OccurrenceDTO createOccurrence(OccurrenceDTO occurrenceDTO);

    OccurrenceDTO getOccurrenceByUuid(String uuid);

    List<OccurrenceDTO> getOccurrencesByStatus(StatusEnum status);

    List<OccurrenceDTO> getAllOccurrences();

    List<OccurrenceDTO> getOccurrencesForEmailJob(String emailJobUuid, LocalDateTime dateFrom, LocalDateTime dateTo);

    OccurrenceDTO updateOccurrence(String uuid, OccurrenceDTO occurrenceDTO);

    void deleteOccurrence(String uuid);

    List<OccurrenceDTO> getOccurrencesByErrorDescription(String errorDescription);
}