/**
 * @author : tadiewa
 * date: 8/3/2025
 */


package zw.com.organicare.exception;

public class LoginFailed extends RuntimeException {
    public LoginFailed(String message) {
        super(message);
    }
}
