/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.service.patientCardService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zw.com.organicare.dto.RequestForm.RequestFormDto;
import zw.com.organicare.dto.patientCard.PatientCardDto;
import zw.com.organicare.dto.patientCard.SonographerReportDto;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.exception.UserNotFound;
import zw.com.organicare.model.*;
import zw.com.organicare.repository.*;
import zw.com.organicare.security.JwtService;
import zw.com.organicare.service.authService.AuthService;
import zw.com.organicare.utils.CodeGeneratorService;
import zw.com.organicare.utils.PatientCardMapper;
import zw.com.organicare.utils.RequestFormMapper;
import zw.com.organicare.utils.SonographerReportMapper;


@Service
@RequiredArgsConstructor
@Slf4j
public class PatientCardServiceImpl implements PatientCardService {
    private final PatientRepository patientRepository;
    private final PatientCardRepository patientCardRepository;
    private final RequestFormRepository requestFormRepository;
    private final UserRepository userRepository;
    private final SonographerReportRepository sonographerReportRepository;
    private final AuthService authService;
    private final CodeGeneratorService codeGeneratorService;
    private final AppointmentRepository appointmentRepository;

    @Override
    public PatientCardDto createPatientCard(Long patientId, PatientCardDto dto) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFound("Patient not found"));

        var appointment = appointmentRepository.findAllByPatient_PatientId(patientId)
                .stream()
                .filter(app -> Boolean.FALSE.equals(app.getIsCompleted()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No completed appointment found for patient"));

        User currentUser = authService.getAuthenticatedUser();
        if (Boolean.FALSE == currentUser.getIsActive()) {
            throw new UserNotFound("User is not active");
        }
        PatientCard card = new PatientCard();
        card.setCardNumber(codeGeneratorService.generateCardNumber());
        card.setPatient(patient);
        card.setDoctorSignature(currentUser.getFullName());
        card.setDiagnosis(dto.getDiagnosis());
        card.setRemarks(dto.getRemarks());
        card.setTreatmentPlan(dto.getTreatmentPlan());
        card.setDuration(dto.getDuration());
        card.setReviewDate(dto.getReviewDate());
        card.setPatientHistory(dto.getPatientHistory());
        card.setFamilyHistory(dto.getFamilyHistory());
        card.setSocialHistory(dto.getSocialHistory());
        card.setPresentingComplaints(dto.getPresentingComplaints());
        card.setPatientSignature(dto.getPatientSignature());
        card.setUltrasoundRequestFlag(dto.getUltrasoundRequestFlag());
        card.setWeight(appointment.getWeight());
        card.setHeight(appointment.getHeight());

        PatientCard saved = patientCardRepository.save(card);
        return PatientCardMapper.toDto(saved);
    }

    @Override
    public PatientCardDto getPatientCard(Long cardId) {
        PatientCard card = patientCardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
        return PatientCardMapper.toDto(card);
    }

    @Override
    public PatientCardDto updatePatientCard(Long cardId, PatientCardDto dto) {
        PatientCard card = patientCardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        card.setDiagnosis(dto.getDiagnosis());
        card.setRemarks(dto.getRemarks());
        card.setTreatmentPlan(dto.getTreatmentPlan());
        card.setDuration(dto.getDuration());
        card.setReviewDate(dto.getReviewDate());
        // check for request form flag
        card.setUltrasoundRequestFlag(dto.getUltrasoundRequestFlag());

        PatientCard updated = patientCardRepository.save(card);
        return PatientCardMapper.toDto(updated);
    }

    @Override
    public RequestFormDto addRequestForm(Long cardId, RequestFormDto dto) {
        PatientCard card = patientCardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        RequestForm form = new RequestForm();
        form.setMedicalAid(dto.getMedicalAid());
        form.setLmp(dto.getLmp());
        form.setClinicalHistory(dto.getClinicalHistory());
        form.setReferringDoctor(dto.getReferringDoctor());
        form.setDate(dto.getDate());
        form.setUltraSoundRequestedOn(dto.getUltraSoundRequestedOn());
        form.setPatientCard(card);
       // form.setRequestedBy(dto.getReferringDoctor());

        RequestForm savedForm = requestFormRepository.save(form);
        return RequestFormMapper.toDto(savedForm);
    }

    @Override
    public SonographerReportDto addSonographerReport(Long requestFormId, SonographerReportDto dto) {


       User currentUser = authService.getAuthenticatedUser();

      log.info("Current user:------------------------> {}", currentUser.getFullName());

     if(Boolean.FALSE==currentUser.getIsActive()){
         throw new UserNotFound("User is not active");}

        RequestForm requestForm = requestFormRepository.findById(requestFormId)
                .orElseThrow(() -> new ResourceNotFoundException("RequestForm not found"));


        SonographerReport report = SonographerReportMapper.toEntity(dto, requestForm, currentUser);


        SonographerReport saved = sonographerReportRepository.save(report);


        return SonographerReportMapper.toDto(saved);
    }



}
