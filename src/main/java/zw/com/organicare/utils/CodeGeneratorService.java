/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.utils;

import org.springframework.stereotype.Component;
import zw.com.organicare.model.PatientCard;
import zw.com.organicare.repository.PatientCardRepository;
import zw.com.organicare.repository.SaleRepository;

import java.security.SecureRandom;

@Component
public class CodeGeneratorService {

    private final PatientCardRepository patientCardRepository;
    private final SaleRepository saleRepository;

    private static final String PREFIX = "RN-";
    private static final String ALPHANUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int SUFFIX_LENGTH = 6;

    public CodeGeneratorService(PatientCardRepository patientCardRepository , SaleRepository saleRepository) {
        this.patientCardRepository = patientCardRepository;
        this.saleRepository = saleRepository;
    }

    public String generateCardNumber() {
        // Find the latest card by ID
        PatientCard lastCard = patientCardRepository
                .findTopByOrderByPatientCardIdDesc()
                .orElse(null);

        long nextId = (lastCard == null) ? 1 : lastCard.getPatientCardId() + 1;

        return String.format("PC-%04d", nextId); // e.g. PC-0001
    }


    public String generateRandomSuffix() {
        StringBuilder sb = new StringBuilder(SUFFIX_LENGTH);
        for (int i = 0; i < SUFFIX_LENGTH; i++) {
            sb.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }
    public String generateSaleReceipt() {
        return PREFIX + generateRandomSuffix();
    }
}
