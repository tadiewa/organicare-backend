/**
 * @author : tadiewa
 * date: 9/12/2025
 */


package zw.com.organicare.utils;


import zw.com.organicare.dto.patient.EmergencyContactDto;
import zw.com.organicare.dto.patient.PatientDto;
import zw.com.organicare.dto.referringPrac.ReferringPracDto;
import zw.com.organicare.model.EmergencyContact;
import zw.com.organicare.model.Patient;
import zw.com.organicare.model.ReferringPractitioner;


public class PatientMapper {

    public static EmergencyContactDto mapEmergencyContact(EmergencyContact contact) {
        if (contact == null) return null;

        return EmergencyContactDto.builder()
                .emergencyContactId(contact.getEmergencyContactId())
                .name(contact.getName())
                .phone(contact.getPhone())
                .relationship(contact.getRelationship().toString())
                .build();
    }

    public static ReferringPracDto mapReferringPractitioner(ReferringPractitioner rp) {
        if (rp == null) return null;

        return ReferringPracDto.builder()
                .rp_Id(rp.getRp_Id())
                .commission_rate(rp.getCommission_rate())
                .active(rp.getUser().getIsActive())
                .userId(rp.getUser() != null ? rp.getUser().getUserId() : null)
                .build();
    }

    public static PatientDto mapPatient(Patient patient) {
        if (patient == null) return null;

        return PatientDto.builder()
                .patientId(patient.getPatientId())
                .fullName(patient.getFullName())
                .dob(patient.getDob())
                .gender(patient.getGender().toString())
                .contactInfo(patient.getContactInfo())
                .address(patient.getAddress())
                .emergencyContact(mapEmergencyContact(patient.getEmergencyContact()))
                //.referringPractitioner(mapReferringPractitioner(patient.getReferringPractitioner()))
                .build();
    }
}

