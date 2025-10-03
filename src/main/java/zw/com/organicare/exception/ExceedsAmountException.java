/**
 * @author : tadiewa
 * date: 10/2/2025
 */


package zw.com.organicare.exception;

public class ExceedsAmountException extends RuntimeException{
    public ExceedsAmountException(String message) {
        super(message);
    }

}