package com.example.EmailSender.service.impl;
import com.example.EmailSender.domain.Occurrence;
import com.example.EmailSender.enumeration.StatusEnum;
import com.example.EmailSender.dto.OccurrenceDTO;
import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.mapper.OccurrenceMapper;
import com.example.EmailSender.repository.OccurrenceRepository;
import com.example.EmailSender.service.OccurrenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OccurrenceServiceImpl implements OccurrenceService {

    private final OccurrenceRepository occurrenceRepository;
    private final OccurrenceMapper occurrenceMapper;

    @Override
    public OccurrenceDTO createOccurrence(OccurrenceDTO occurrenceDTO) {
        Occurrence occurrence = occurrenceMapper.toEntity(occurrenceDTO);
        Occurrence savedOccurrence = occurrenceRepository.save(occurrence);
        return occurrenceMapper.toDto(savedOccurrence);
    }

    @Override
    public OccurrenceDTO getOccurrenceByUuid(String uuid) {
        Occurrence occurrence = occurrenceRepository.findByUuid(uuid);
        if (occurrence == null) {
            throw new ResourceNotFoundException("Occurrence with UUID: " + uuid + " not found");
        }
        return occurrenceMapper.toDto(occurrence);
    }

    @Override
    public List<OccurrenceDTO> getOccurrencesByStatus(StatusEnum status) {
        List<Occurrence> occurrences = occurrenceRepository.findByStatus(status);
        return occurrences.stream()
                .map(occurrenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OccurrenceDTO> getAllOccurrences() {
        List<Occurrence> occurrences = occurrenceRepository.findAll();
        return occurrences.stream()
                .map(occurrenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OccurrenceDTO> getOccurrencesForEmailJob(String emailJobUuid, LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<Occurrence> occurrences = occurrenceRepository.findOccurrencesForEmailJob(emailJobUuid, dateFrom, dateTo);
        return occurrences.stream()
                .map(occurrenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OccurrenceDTO updateOccurrence(String uuid, OccurrenceDTO occurrenceDTO) {
        Occurrence existingOccurrence = occurrenceRepository.findByUuid(uuid);
        if (existingOccurrence == null) {
            throw new ResourceNotFoundException("Occurrence with UUID: " + uuid + " not found");
        }

        if (occurrenceDTO.getUuid() != null && !occurrenceDTO.getUuid().equals(existingOccurrence.getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'uuid' field cannot be updated.");
        }

        if (occurrenceDTO.getCreatedOn() != null && !occurrenceDTO.getCreatedOn().equals(existingOccurrence.getCreatedOn())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'createdOn' field cannot be updated.");
        }

        occurrenceMapper.updateOccurrenceFromDto(occurrenceDTO, existingOccurrence);
        Occurrence updatedOccurrence = occurrenceRepository.save(existingOccurrence);
        return occurrenceMapper.toDto(updatedOccurrence);
    }


    @Override
    public void deleteOccurrence(String uuid) {
        Occurrence occurrence = occurrenceRepository.findByUuid(uuid);
        if (occurrence == null) {
            throw new ResourceNotFoundException("Occurrence with UUID: " + uuid + " not found");
        }
        occurrenceRepository.deleteByUuid(uuid);
    }

    @Override
    public List<OccurrenceDTO> getOccurrencesByErrorDescription(String errorDescription) {
        List<Occurrence> occurrences = occurrenceRepository.findByErrorDescription(errorDescription);
        return occurrences.stream()
                .map(occurrenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OccurrenceDTO> getOccurrenceByEmailJob(String emailJobUuid) {
       List<Occurrence> occurrences =occurrenceRepository.findByEmailJobUuid(emailJobUuid);
        return occurrences.stream()
                .map(occurrenceMapper::toDto)
                .collect(Collectors.toList());

    }
}