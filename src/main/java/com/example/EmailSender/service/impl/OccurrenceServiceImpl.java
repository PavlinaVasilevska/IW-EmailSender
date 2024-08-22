package com.example.EmailSender.service.impl;

import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.enumeration.StatusEnum;
import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.repository.EmailJobRepository;
import com.example.EmailSender.repository.OccurrenceRepository;
import com.example.EmailSender.domain.Occurrence;
import com.example.EmailSender.dto.OccurrenceDTO;
import com.example.EmailSender.mapper.OccurrenceMapper;
import com.example.EmailSender.service.OccurrenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OccurrenceServiceImpl implements OccurrenceService {

    private final OccurrenceRepository occurrenceRepository;
    private final OccurrenceMapper occurrenceMapper;
    private final EmailJobRepository emailJobRepository;

    @Override
    public OccurrenceDTO createOccurrence(OccurrenceDTO occurrenceDTO) {
        Occurrence occurrence = occurrenceMapper.toEntity(occurrenceDTO);

        EmailJob emailJob = emailJobRepository.findByUuid(occurrenceDTO.getEmailJob().getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("EmailJob not found"));
        occurrence.setEmailJob(emailJob);
        occurrence.setStatus(occurrenceDTO.getStatus());
        occurrence.setErrorDescription(occurrenceDTO.getErrorDescription());

        Occurrence savedOccurrence = occurrenceRepository.save(occurrence);
        return occurrenceMapper.toDto(savedOccurrence);
    }

    @Override
    public Optional<OccurrenceDTO> getOccurrenceByUuid(String uuid) {
        Occurrence occurrence = occurrenceRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Occurrence not found with UUID: " + uuid));
        return Optional.of(occurrenceMapper.toDto(occurrence));
    }

    @Override
    public List<OccurrenceDTO> getOccurrencesByStatus(StatusEnum status) {
        List<Occurrence> occurrences = occurrenceRepository.findByStatus(status);
        if (occurrences.isEmpty()) {
            throw new ResourceNotFoundException("No occurrences found with status: " + status);
        }
        return occurrences.stream()
                .map(occurrenceMapper::toDto) // Convert entity to DTO
                .collect(Collectors.toList());
    }
    @Override
    public List<OccurrenceDTO> getAllOccurrences() {
        List<Occurrence> occurrences = occurrenceRepository.findAll();
        if (occurrences.isEmpty()) {
            throw new ResourceNotFoundException("No occurrences found.");
        }
        return occurrenceMapper.toDtoList(occurrences);
    }

    @Override
    public List<OccurrenceDTO> getOccurrencesByEmailJobUuid(String emailJobUuid) {
        List<Occurrence> occurrences = occurrenceRepository.findByEmailJobUuid(emailJobUuid);
        if (occurrences.isEmpty()) {
            throw new ResourceNotFoundException("No occurrences found for EmailJob with UUID: " + emailJobUuid);
        }
        return occurrences.stream()
                .map(occurrenceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OccurrenceDTO updateOccurrence(String uuid, OccurrenceDTO occurrenceDTO) {
        Occurrence occurrence = occurrenceRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Occurrence not found with UUID: " + uuid));

        occurrence.setStatus(occurrenceDTO.getStatus());
        occurrence.setErrorDescription(occurrenceDTO.getErrorDescription());

        Occurrence updatedOccurrence = occurrenceRepository.save(occurrence);
        return occurrenceMapper.toDto(updatedOccurrence);
    }

    @Override
    public void deleteOccurrence(String uuid) {
        Occurrence occurrence = occurrenceRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Occurrence not found with UUID: " + uuid));
        occurrenceRepository.delete(occurrence);
    }

    @Override
    public List<OccurrenceDTO> getOccurrencesByErrorDescription(String errorDescription) {
        List<Occurrence> occurrences = occurrenceRepository.findByErrorDescription(errorDescription);
        if (occurrences.isEmpty()) {
            throw new ResourceNotFoundException("No occurrences found with error description: " + errorDescription);
        }
        return occurrences.stream()
                .map(occurrenceMapper::toDto) // Convert entity to DTO
                .collect(Collectors.toList());
    }
}
