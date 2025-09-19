/**
 * @author : tadiewa
 * date: 9/19/2025
 */

package zw.com.organicare.service.payment;

import zw.com.organicare.dto.payment.PaymentRequestDto;
import zw.com.organicare.dto.payment.PaymentResponseDto;

import java.util.List;

public interface PaymentService {
    PaymentResponseDto addPayment(Long saleId, PaymentRequestDto request);
    List<PaymentResponseDto> getPaymentsBySale(Long saleId);
}
