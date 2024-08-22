package com.example.EmailSender.api;
import com.example.EmailSender.dto.OccurrenceDTO;
import com.example.EmailSender.enumeration.StatusEnum;
import com.example.EmailSender.infrastructure.EndPoints;
import com.example.EmailSender.service.OccurrenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EndPoints.OCCURRENCES)
public class OccurrenceController {

    private final OccurrenceService occurrenceService;

    @Autowired
    public OccurrenceController(OccurrenceService occurrenceService) {
        this.occurrenceService = occurrenceService;
    }

    @PostMapping
    public ResponseEntity<OccurrenceDTO> createOccurrence(@RequestBody OccurrenceDTO occurrenceDTO) {
        OccurrenceDTO createdOccurrence = occurrenceService.createOccurrence(occurrenceDTO);
        return ResponseEntity.ok(createdOccurrence);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OccurrenceDTO>> getOccurrencesByStatus(@PathVariable("status") StatusEnum status) {
        List<OccurrenceDTO> occurrences = occurrenceService.getOccurrencesByStatus(status);
        return ResponseEntity.ok(occurrences);
    }

    @GetMapping("/{uuid}")
    public Optional<ResponseEntity<OccurrenceDTO>> getOccurrenceByUuid(@PathVariable String uuid) {
        return occurrenceService.getOccurrenceByUuid(uuid).map(ResponseEntity::ok);
    }

    @GetMapping
    public ResponseEntity<List<OccurrenceDTO>> getAllOccurrences() {
        List<OccurrenceDTO> occurrences = occurrenceService.getAllOccurrences();
        return ResponseEntity.ok(occurrences);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<OccurrenceDTO> updateOccurrence(@PathVariable String uuid, @RequestBody OccurrenceDTO occurrenceDTO) {
        OccurrenceDTO updatedOccurrence = occurrenceService.updateOccurrence(uuid, occurrenceDTO);
        return ResponseEntity.ok(updatedOccurrence);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteOccurrence(@PathVariable String uuid) {
        occurrenceService.deleteOccurrence(uuid);
        return ResponseEntity.noContent().build();
    }
}
