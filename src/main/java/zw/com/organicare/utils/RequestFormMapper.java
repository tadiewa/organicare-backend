/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.utils;

import zw.com.organicare.dto.RequestForm.RequestFormDto;
import zw.com.organicare.model.PatientCard;
import zw.com.organicare.model.RequestForm;

public class RequestFormMapper {

    public static RequestFormDto toDto(RequestForm form) {
        if (form == null) return null;

        return RequestFormDto.builder()
                .requestFormId(form.getRequestFormId())
                .medicalAid(form.getMedicalAid())
                .lmp(form.getLmp())
                .clinicalHistory(form.getClinicalHistory())
                .referringDoctor(form.getReferringDoctor())
                .date(form.getDate())
                .ultraSoundRequestedOn(form.getUltraSoundRequestedOn())
                .build();
    }

    public static RequestForm toEntity(RequestFormDto dto, PatientCard patientCard) {
        if (dto == null) return null;

        RequestForm form = new RequestForm();
        form.setRequestFormId(dto.getRequestFormId());
        form.setMedicalAid(dto.getMedicalAid());
        form.setLmp(dto.getLmp());
        form.setClinicalHistory(dto.getClinicalHistory());
        form.setReferringDoctor(dto.getReferringDoctor());
        form.setDate(dto.getDate());
        form.setUltraSoundRequestedOn(dto.getUltraSoundRequestedOn());
        form.setPatientCard(patientCard);

        return form;
    }
}

