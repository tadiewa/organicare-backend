/**
 * @author : tadiewa
 * date: 9/11/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.dto.patient.PatientDto;
import zw.com.organicare.service.patientService.PatientService;

@RestController
@RequestMapping("/api/patient")
@Slf4j
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/register")
    ResponseEntity<PatientDto> register(@RequestBody PatientDto request) {
        log.info("Processing patient registration request for patient--------------------------->: {}", request);
        PatientDto response = patientService.register(request);
        log.info("Patient registered --------------------------->: {}", response);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update")
    ResponseEntity<PatientDto> update(@RequestBody PatientDto request) {
        PatientDto response = patientService.update(request);
        return ResponseEntity.ok(response);
    }
}
