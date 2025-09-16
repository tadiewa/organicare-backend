/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.utils;

import org.springframework.stereotype.Component;
import zw.com.organicare.model.PatientCard;
import zw.com.organicare.repository.PatientCardRepository;

@Component

public class CodeGeneratorService {

    private final PatientCardRepository patientCardRepository;

    public CodeGeneratorService(PatientCardRepository patientCardRepository) {
        this.patientCardRepository = patientCardRepository;
    }

    public String generateCardNumber() {
        // Find the latest card by ID
        PatientCard lastCard = patientCardRepository
                .findTopByOrderByPatientCardIdDesc()
                .orElse(null);

        long nextId = (lastCard == null) ? 1 : lastCard.getPatientCardId() + 1;

        return String.format("PC-%04d", nextId); // e.g. PC-0001
    }
}
