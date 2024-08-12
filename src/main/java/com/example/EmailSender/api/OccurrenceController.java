package com.example.EmailSender.api;
import com.example.EmailSender.dto.OccurrenceDTO;
import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.service.OccurrenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/occurrences")
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

    @GetMapping("/{uuid}")
    public ResponseEntity<OccurrenceDTO> getOccurrenceByUuid(@PathVariable String uuid) {
        return occurrenceService.getOccurrenceByUuid(uuid)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Occurrence not found with uuid: " + uuid));
    }

    @GetMapping("/email-job/{emailJobUuid}")
    public ResponseEntity<OccurrenceDTO> getOccurrenceByEmailJobUuid(@PathVariable String emailJobUuid) {
        return occurrenceService.getOccurrenceByEmailJobUuid(emailJobUuid)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Occurrence not found for email job uuid: " + emailJobUuid));
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
