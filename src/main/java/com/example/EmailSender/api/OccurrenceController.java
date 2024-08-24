package com.example.EmailSender.api;
import com.example.EmailSender.dto.OccurrenceDTO;
import com.example.EmailSender.enumeration.StatusEnum;
import com.example.EmailSender.infrastructure.EndPoints;
import com.example.EmailSender.service.OccurrenceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping(EndPoints.OCCURRENCES)
public class OccurrenceController {

    private final OccurrenceService occurrenceService;

    public OccurrenceController(OccurrenceService occurrenceService) {
        this.occurrenceService = occurrenceService;
    }

    @PostMapping

    public ResponseEntity<OccurrenceDTO> createOccurrence(@RequestBody @Valid OccurrenceDTO occurrenceDTO) {
        OccurrenceDTO createdOccurrence = occurrenceService.createOccurrence(occurrenceDTO);
        return ResponseEntity.status(200).body(createdOccurrence);
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<OccurrenceDTO> getOccurrenceByUuid(@PathVariable String uuid) {
        OccurrenceDTO occurrence = occurrenceService.getOccurrenceByUuid(uuid);
        return ResponseEntity.ok(occurrence);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OccurrenceDTO>> getOccurrencesByStatus(@PathVariable StatusEnum status) {
        List<OccurrenceDTO> occurrences = occurrenceService.getOccurrencesByStatus(status);
        return ResponseEntity.ok(occurrences);
    }

    @GetMapping
    public ResponseEntity<List<OccurrenceDTO>> getAllOccurrences() {
        List<OccurrenceDTO> occurrences = occurrenceService.getAllOccurrences();
        return ResponseEntity.ok(occurrences);
    }

    @GetMapping("/email-job/{uuid}")
    public ResponseEntity<List<OccurrenceDTO>> getOccurrencesByEmailJob(
            @PathVariable String uuid,
            @RequestParam @Valid LocalDateTime dateFrom,
            @RequestParam @Valid LocalDateTime dateTo) {
        List<OccurrenceDTO> occurrences = occurrenceService.getOccurrencesForEmailJob(uuid, dateFrom, dateTo);
        return ResponseEntity.ok(occurrences);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<OccurrenceDTO> updateOccurrence(
            @PathVariable String uuid,
            @RequestBody @Valid OccurrenceDTO occurrenceDTO) {
        OccurrenceDTO updatedOccurrence = occurrenceService.updateOccurrence(uuid, occurrenceDTO);
        return ResponseEntity.ok(updatedOccurrence);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteOccurrence(@PathVariable String uuid) {
        occurrenceService.deleteOccurrence(uuid);
        return ResponseEntity.noContent().build(); // Status 204 for no content
    }

    @GetMapping("/error-description/{errorDescription}")
    public ResponseEntity<List<OccurrenceDTO>> getOccurrencesByErrorDescription(@PathVariable String errorDescription) {
        List<OccurrenceDTO> occurrences = occurrenceService.getOccurrencesByErrorDescription(errorDescription);
        return ResponseEntity.ok(occurrences);
    }
}
