/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.dto.RequestForm.RequestFormDto;
import zw.com.organicare.dto.patientCard.PatientCardDto;
import zw.com.organicare.dto.patientCard.SonographerReportDto;
import zw.com.organicare.service.patientCardService.PatientCardService;

@RestController
@Slf4j
@RequestMapping("/api/patient-card")
public class PatientCardController {

    private final PatientCardService patientCardService;

    public PatientCardController(PatientCardService patientCardService) {
        this.patientCardService = patientCardService;
    }

    // 1. Create a new patient card for a patient
    @PostMapping("/patient/{patientId}")
    public ResponseEntity<PatientCardDto> createCard(@PathVariable Long patientId,
                                                     @RequestBody PatientCardDto patientCardDto) {
        PatientCardDto savedCard = patientCardService.createPatientCard(patientId, patientCardDto);
        return ResponseEntity.ok(savedCard);
    }

    // 2. Get patient card by ID
    @GetMapping("/{cardId}")
    public ResponseEntity<PatientCardDto> getCard(@PathVariable Long cardId) {
        return ResponseEntity.ok(patientCardService.getPatientCard(cardId));
    }

    // 3. Update patient card (diagnosis, treatment, remarks, etc.)
    @PutMapping("/{cardId}")
    public ResponseEntity<PatientCardDto> updateCard(@PathVariable Long cardId,
                                                     @RequestBody PatientCardDto patientCardDto) {
        return ResponseEntity.ok(patientCardService.updatePatientCard(cardId, patientCardDto));
    }

    // 4. Add a request form to a patient card
    @PostMapping("/{cardId}/requests")
    public ResponseEntity<RequestFormDto> addRequestForm(@PathVariable Long cardId,
                                                         @RequestBody RequestFormDto requestFormDto) {
        RequestFormDto savedRequest = patientCardService.addRequestForm(cardId, requestFormDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
    }

    @PostMapping("/ultra-repo/{requestFormId}")
    public ResponseEntity<SonographerReportDto> addReport(@PathVariable Long requestFormId,
                                                          @RequestBody SonographerReportDto dto
    ) {
        SonographerReportDto report = patientCardService.addSonographerReport(requestFormId, dto);
        return ResponseEntity.ok(report);
    }
}
