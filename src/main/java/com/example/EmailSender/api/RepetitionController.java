package com.example.EmailSender.api;
import com.example.EmailSender.dto.RepetitionDTO;
import com.example.EmailSender.infrastructure.EndPoints;
import com.example.EmailSender.service.RepetitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EndPoints.REPETITIONS)
public class RepetitionController {

    private RepetitionService repetitionService;

    public RepetitionController(RepetitionService repetitionService) {
        this.repetitionService = repetitionService;
    }

    @PostMapping
    public ResponseEntity<RepetitionDTO> createRepetition(@RequestBody RepetitionDTO repetitionDTO) {
        RepetitionDTO createdRepetition = repetitionService.createRepetition(repetitionDTO);
        return ResponseEntity.ok(createdRepetition);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Optional<RepetitionDTO>> getRepetitionByUuid(@PathVariable String uuid) {
        Optional<RepetitionDTO> repetitionDTO = repetitionService.getRepetitionByUuid(uuid);
        return ResponseEntity.ok(repetitionDTO);
    }

    @GetMapping
    public ResponseEntity<List<RepetitionDTO>> getAllRepetitions() {
        List<RepetitionDTO> repetitions = repetitionService.getAllRepetitions();
        return ResponseEntity.ok(repetitions);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<RepetitionDTO> updateRepetition(@PathVariable String uuid, @RequestBody RepetitionDTO repetitionDTO) {
        RepetitionDTO updatedRepetition = repetitionService.updateRepetition(uuid, repetitionDTO);
        return ResponseEntity.ok(updatedRepetition);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteRepetition(@PathVariable String uuid) {
        repetitionService.deleteRepetition(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/frequency/{frequency}")
    public ResponseEntity<List<RepetitionDTO>> findByFrequency(@PathVariable String frequency) {
        List<RepetitionDTO> repetitions = repetitionService.findByFrequency(frequency);
        if (repetitions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(repetitions);
    }

    @GetMapping("/number-of-tries/{numberOfTries}")
    public ResponseEntity<List<RepetitionDTO>> findByNumberOfTries(@PathVariable Integer numberOfTries) {
        List<RepetitionDTO> repetitions = repetitionService.findByNumberOfTries(numberOfTries);
        if (repetitions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(repetitions);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Endpoint is working");
    }
}
