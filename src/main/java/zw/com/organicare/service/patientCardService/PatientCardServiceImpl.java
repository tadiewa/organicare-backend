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
import zw.com.organicare.service.authService.CustomUserDetailsService;
import zw.com.organicare.utils.CodeGeneratorService;
import zw.com.organicare.utils.PatientCardMapper;
import zw.com.organicare.utils.RequestFormMapper;
import zw.com.organicare.utils.SonographerReportMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientCardServiceImpl implements PatientCardService {
    private final PatientRepository patientRepository;
    private final PatientCardRepository patientCardRepository;
    private final RequestFormRepository requestFormRepository;
    private final UserRepository userRepository;
    private final SonographerReportRepository sonographerReportRepository;
    private final JwtService jwtService;
    private final HttpServletRequest request;
    private final CodeGeneratorService codeGeneratorService;

    @Override
    public PatientCardDto createPatientCard(Long patientId, PatientCardDto dto) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFound("Patient not found"));

        PatientCard card = new PatientCard();
        card.setCardNumber(codeGeneratorService.generateCardNumber());
        card.setPatient(patient);
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

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("JWT token missing");
        }
        String token = authorizationHeader.substring(7);


        String username = jwtService.extractUsername(token);


        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFound("User not found with email: " + username));
     log.info("Current user:------------------------> {}", currentUser.getFullName());


     log.info("Is user active? ------------------------> {}", currentUser.getIsActive());
     if(currentUser.getIsActive()==false){
         throw new RuntimeException("User is not active");}

        RequestForm requestForm = requestFormRepository.findById(requestFormId)
                .orElseThrow(() -> new ResourceNotFoundException("RequestForm not found"));


        SonographerReport report = SonographerReportMapper.toEntity(dto, requestForm, currentUser);


        SonographerReport saved = sonographerReportRepository.save(report);


        return SonographerReportMapper.toDto(saved);
    }



}
