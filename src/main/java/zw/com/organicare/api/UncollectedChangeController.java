/**
 * @author : tadiewa
 * date: 9/19/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.dto.change.UncollectedChangeResponse;
import zw.com.organicare.service.change.UncollectedChangeService;

import java.util.List;

@RestController
@RequestMapping("/api/uncollected-change")
@RequiredArgsConstructor
public class UncollectedChangeController {

    private final UncollectedChangeService uncollectedChangeService;

    @PostMapping("/collect/{id}")
    public ResponseEntity<UncollectedChangeResponse> collectChange(@PathVariable Long id) {
        UncollectedChangeResponse response = uncollectedChangeService.collectChange(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pending/{patientId}")
    public ResponseEntity<List<UncollectedChangeResponse>> getPendingChanges(@PathVariable Long patientId) {
        List<UncollectedChangeResponse> changes = uncollectedChangeService.getPendingChangesForPatient(patientId);
        return ResponseEntity.ok(changes);
    }
}


