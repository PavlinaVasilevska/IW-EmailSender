package com.example.EmailSender.service.impl;

import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.repository.RepetitionRepository;
import com.example.EmailSender.domain.Repetition;
import com.example.EmailSender.dto.RepetitionDTO;
import com.example.EmailSender.mapper.RepetitionMapper;
import com.example.EmailSender.service.RepetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RepetitionServiceImpl implements RepetitionService {

    private final RepetitionRepository repetitionRepository;
    private final RepetitionMapper repetitionMapper;

    @Override
    public RepetitionDTO createRepetition(RepetitionDTO repetitionDTO) {
        // Validate input
        if (repetitionDTO == null) {
            throw new IllegalArgumentException("RepetitionDTO cannot be null");
        }
        if (repetitionDTO.getFrequency() == null || repetitionDTO.getFrequency().isEmpty()) {
            throw new IllegalArgumentException("Frequency cannot be null or empty");
        }
        if (repetitionDTO.getNumberOfTries() == null || repetitionDTO.getNumberOfTries() < 1) {
            throw new IllegalArgumentException("Number of tries must be greater than 0");
        }

        // Convert DTO to entity
        Repetition repetition = repetitionMapper.repetitionDTOToRepetition(repetitionDTO);


        // Save entity
        Repetition savedRepetition = repetitionRepository.save(repetition);

        // Convert back to DTO
        return repetitionMapper.repetitionToRepetitionDTO(savedRepetition);
    }

    @Override
    public Optional<RepetitionDTO> getRepetitionByUuid(String uuid) {
        Repetition repetition = repetitionRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Repetition not found with UUID: " + uuid));
        return Optional.ofNullable(repetitionMapper.repetitionToRepetitionDTO(repetition));
    }

    @Override
    public List<RepetitionDTO> getAllRepetitions() {
        List<Repetition> repetitions = repetitionRepository.findAll();
        return repetitions.stream()
                .map(repetitionMapper::repetitionToRepetitionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RepetitionDTO updateRepetition(String uuid, RepetitionDTO repetitionDTO) {
        Optional<Repetition> existingRepetition = repetitionRepository.findByUuid(uuid);
        if (existingRepetition.isPresent()) {
            Repetition repetition = existingRepetition.get();
            repetition.setFrequency(repetitionDTO.getFrequency());
            repetition.setNumberOfTries(repetitionDTO.getNumberOfTries());
            Repetition updatedRepetition = repetitionRepository.save(repetition);
            return repetitionMapper.repetitionToRepetitionDTO(updatedRepetition);
        }
        throw new RuntimeException("Repetition not found with uuid: " + uuid);
    }

    @Override
    public void deleteRepetition(String uuid) {
        Optional<Repetition> repetition = repetitionRepository.findByUuid(uuid);
        if (repetition.isPresent()) {
            repetitionRepository.delete(repetition.get());
        } else {
            throw new RuntimeException("Repetition not found with uuid: " + uuid);
        }
    }

    @Override
    public List<RepetitionDTO> findByFrequency(String frequency) {
        List<Repetition> repetitions = repetitionRepository.findByFrequency(frequency);
        if (repetitions.isEmpty()) {
            throw new ResourceNotFoundException("No repetitions found with frequency: " + frequency);
        }
        return repetitions.stream()
                .map(repetitionMapper::repetitionToRepetitionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RepetitionDTO> findByNumberOfTries(Integer numberOfTries) {
        List<Repetition> repetitions = repetitionRepository.findByNumberOfTries(numberOfTries);
        if (repetitions.isEmpty()) {
            throw new ResourceNotFoundException("No repetitions found with number of tries: " + numberOfTries);
        }
        return repetitions.stream()
                .map(repetitionMapper::repetitionToRepetitionDTO)
                .collect(Collectors.toList());
    }


}
