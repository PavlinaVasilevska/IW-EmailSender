package com.example.EmailSender.service;

import com.example.EmailSender.dto.RepetitionDTO;

import java.util.List;
import java.util.Optional;

public interface RepetitionService {
    RepetitionDTO createRepetition(RepetitionDTO repetitionDTO);
    Optional<RepetitionDTO> getRepetitionByUuid(String uuid);
    List<RepetitionDTO> getAllRepetitions();
    RepetitionDTO updateRepetition(String uuid, RepetitionDTO repetitionDTO);
    void deleteRepetition(String uuid);
    List<RepetitionDTO> findByFrequency(String frequency);
    List<RepetitionDTO> findByNumberOfTries(Integer numberOfTries);
}
