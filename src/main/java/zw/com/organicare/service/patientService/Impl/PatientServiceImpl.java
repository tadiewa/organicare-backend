/**
 * @author : tadiewa
 * date: 9/12/2025
 */


package zw.com.organicare.service.patientService.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.com.organicare.constants.Gender;
import zw.com.organicare.constants.Relationship;
import zw.com.organicare.dto.patient.EmergencyContactDto;
import zw.com.organicare.dto.patient.PatientDto;
import zw.com.organicare.model.EmergencyContact;
import zw.com.organicare.model.Patient;
import zw.com.organicare.model.ReferringPractitioner;
import zw.com.organicare.repository.PatientRepository;
import zw.com.organicare.repository.ReferringPractitionerRespository;
import zw.com.organicare.service.patientService.PatientService;
import zw.com.organicare.utils.PatientMapper;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final ReferringPractitionerRespository referringPractitionerRepository;

    @Transactional
    public PatientDto register(PatientDto patientDto) {
        // 1. Map EmergencyContact
        EmergencyContact emergencyContact = null;
        if (patientDto.getEmergencyContact() != null) {
            Relationship relationship = null;
            if (patientDto.getEmergencyContact().getRelationship() != null) {
                relationship = Relationship.valueOf(patientDto.getEmergencyContact().getRelationship());
            }

            emergencyContact = EmergencyContact.builder()
                    .name(patientDto.getEmergencyContact().getName())
                    .phone(patientDto.getEmergencyContact().getPhone())
                    .relationship(relationship)
                    .build();
        }

        // 2. Fetch ReferringPractitioner by ID (if provided)
        ReferringPractitioner rp = null;
        if (patientDto.getRp_Id() != null) {
            rp = referringPractitionerRepository.findById(patientDto.getRp_Id())
                    .orElseThrow(() -> new RuntimeException("Referring Practitioner not found with id: " + patientDto.getRp_Id()));
        }

        // 3. Map DTO → Patient entity
        Patient patient = Patient.builder()
                .fullName(patientDto.getFullName())
                .dob(patientDto.getDob())
                .gender(Gender.valueOf(patientDto.getGender()))
                .contactInfo(patientDto.getContactInfo())
                .address(patientDto.getAddress())
                .emergencyContact(emergencyContact)
                .referringPractitioner(rp)
                .build();

        // 4. Save entity
        patient = patientRepository.save(patient);

        // 5. Map entity → DTO for response
        PatientDto responseDto = PatientMapper.mapPatient(patient);
        return responseDto;
    }

    @Override
    @Transactional
    public PatientDto update(PatientDto patientDto) {
        // 1. Fetch existing patient
        Patient patient = patientRepository.findById(patientDto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientDto.getPatientId()));

        // 2. Update simple fields if present
        Optional.ofNullable(patientDto.getFullName()).ifPresent(patient::setFullName);
        Optional.ofNullable(patientDto.getDob()).ifPresent(patient::setDob);
        Optional.ofNullable(patientDto.getGender())
                .map(Gender::valueOf)
                .ifPresent(patient::setGender);
        Optional.ofNullable(patientDto.getContactInfo()).ifPresent(patient::setContactInfo);
        Optional.ofNullable(patientDto.getAddress()).ifPresent(patient::setAddress);

        // 3. Update EmergencyContact if provided
        if (patientDto.getEmergencyContact() != null) {
            EmergencyContactDto ecDto = patientDto.getEmergencyContact();
            EmergencyContact ec = Optional.ofNullable(patient.getEmergencyContact())
                    .orElseGet(EmergencyContact::new);

            Optional.ofNullable(ecDto.getName()).ifPresent(ec::setName);
            Optional.ofNullable(ecDto.getPhone()).ifPresent(ec::setPhone);
            Optional.ofNullable(ecDto.getRelationship())
                    .ifPresent(r -> ec.setRelationship(Relationship.valueOf(r)));

            patient.setEmergencyContact(ec);
        }

        // 4. Update ReferringPractitioner if rp_Id provided
        Optional.ofNullable(patientDto.getRp_Id()).ifPresent(rpId -> {
            ReferringPractitioner rp = referringPractitionerRepository.findById(rpId)
                    .orElseThrow(() -> new RuntimeException("Referring Practitioner not found with id: " + rpId));
            patient.setReferringPractitioner(rp);
        });

        // 5. Save updated patient
        Patient updatedPatient = patientRepository.save(patient);

        // 6. Map entity → DTO
        return PatientMapper.mapPatient(updatedPatient);
    }

}
