/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ProductCodeGenerator {

    private static final String PREFIX = "Pd-";
    private static final String ALPHANUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int SUFFIX_LENGTH = 6; // Pd-XXXXXX

    public String generateRandomSuffix() {
        StringBuilder sb = new StringBuilder(SUFFIX_LENGTH);
        for (int i = 0; i < SUFFIX_LENGTH; i++) {
            sb.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }

    public String generate() {
        return PREFIX + generateRandomSuffix();
    }
}

