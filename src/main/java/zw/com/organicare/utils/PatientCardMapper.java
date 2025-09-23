/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.utils;

import zw.com.organicare.dto.patientCard.PatientCardDto;
import zw.com.organicare.model.Patient;
import zw.com.organicare.model.PatientCard;

public class PatientCardMapper {

    public static PatientCardDto toDto(PatientCard card) {
        if (card == null) return null;

        return PatientCardDto.builder()
                .patientCardId(card.getPatientCardId())
                .cardNumber(card.getCardNumber())
                .patientHistory(card.getPatientHistory())
                .familyHistory(card.getFamilyHistory())
                .socialHistory(card.getSocialHistory())
                .presentingComplaints(card.getPresentingComplaints())
                .diagnosis(card.getDiagnosis())
                .remarks(card.getRemarks())
                .treatmentPlan(card.getTreatmentPlan())
                .duration(card.getDuration())
                .ultrasoundRequestFlag(card.getUltrasoundRequestFlag())
                .reviewDate(card.getReviewDate())
                .patientSignature(card.getPatientSignature())
                .doctorSignature(card.getDoctorSignature())
                .patientId(card.getPatient() != null ? card.getPatient().getPatientId() : null)
                .requestForms(card.getRequestForms() != null
                        ? card.getRequestForms().stream()
                        .map(RequestFormMapper::toDto)
                        .toList()
                        : null)
                .build();
    }

    public static PatientCard toEntity(PatientCardDto dto, Patient patient) {
        if (dto == null) return null;

        PatientCard card = new PatientCard();
        card.setPatientCardId(dto.getPatientCardId());
        card.setCardNumber(dto.getCardNumber());
        card.setPatientHistory(dto.getPatientHistory());
        card.setFamilyHistory(dto.getFamilyHistory());
        card.setSocialHistory(dto.getSocialHistory());
        card.setPresentingComplaints(dto.getPresentingComplaints());
        card.setDiagnosis(dto.getDiagnosis());
        card.setRemarks(dto.getRemarks());
        card.setTreatmentPlan(dto.getTreatmentPlan());
        card.setDuration(dto.getDuration());
        card.setReviewDate(dto.getReviewDate());
        card.setPatientSignature(dto.getPatientSignature());
        card.setDoctorSignature(dto.getDoctorSignature());
        card.setUltrasoundRequestFlag(dto.getUltrasoundRequestFlag());
        card.setPatient(patient);

        return card;
    }
}

