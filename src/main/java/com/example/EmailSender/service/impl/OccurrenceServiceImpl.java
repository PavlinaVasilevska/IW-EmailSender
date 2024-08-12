package com.example.EmailSender.service.impl;

import com.example.EmailSender.domain.EmailJob;
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
        EmailJob emailJob=emailJobRepository.findByUuid(occurrenceDTO.getEmailJob().getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("EmailJob not found"));
        occurrence.setEmailJob(emailJob);
        occurrence.setStatus(occurrenceDTO.getStatus());
        occurrence.setErrorDescription(occurrenceDTO.getErrorDescription());

        Occurrence savedOccurrence = occurrenceRepository.save(occurrence);
        return occurrenceMapper.toDTO(savedOccurrence);
    }

    @Override
    public Optional<OccurrenceDTO> getOccurrenceByUuid(String uuid) {
        Optional<Occurrence> occurrence = occurrenceRepository.findByUuid(uuid);
        return occurrence.map(occurrenceMapper::toDTO);
    }

    @Override
    public Optional<OccurrenceDTO> getOccurrenceByEmailJobUuid(String emailJobUuid) {
        Optional<Occurrence> occurrence = occurrenceRepository.findByEmailJobUuid(emailJobUuid);
        return occurrence.map(occurrenceMapper::toDTO);
    }

    @Override
    public List<OccurrenceDTO> getOccurrencesByStatus(Integer status) {
        List<Occurrence> occurrences = occurrenceRepository.findByStatus(status);
        return occurrences.stream()
                .map(occurrenceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OccurrenceDTO> getAllOccurrences() {
        List<Occurrence> occurrences = occurrenceRepository.findAll();
        return occurrences.stream()
                .map(occurrenceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OccurrenceDTO updateOccurrence(String uuid, OccurrenceDTO occurrenceDTO) {
        Occurrence occurrence = occurrenceRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Occurrence not found with uuid: " + uuid));

        occurrence.setStatus(occurrenceDTO.getStatus());
        occurrence.setErrorDescription(occurrenceDTO.getErrorDescription());

        // Map the EmailJobDTO from the DTO to the entity and set it
        if (occurrenceDTO.getEmailJob() != null) {
            // Convert EmailJobDTO to EmailJob entity
            occurrence.setEmailJob(occurrenceMapper.toEntity(occurrenceDTO).getEmailJob());
        }

        Occurrence updatedOccurrence = occurrenceRepository.save(occurrence);
        return occurrenceMapper.toDTO(updatedOccurrence);
    }

    @Override
    public void deleteOccurrence(String uuid) {
        Optional<Occurrence> occurrence = occurrenceRepository.findByUuid(uuid);
        if (occurrence.isPresent()) {
            occurrenceRepository.deleteByUuid(uuid);
        } else {
            throw new RuntimeException("Occurrence not found with uuid: " + uuid);
        }
    }
}
