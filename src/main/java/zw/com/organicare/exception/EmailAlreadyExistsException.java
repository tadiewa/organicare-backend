/**
 * @author : tadiewa
 * date: 7/25/2025
 */


package zw.com.organicare.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
