/**
 * @author : tadiewa
 * date: 9/19/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.dto.payment.PaymentRequestDto;
import zw.com.organicare.dto.payment.PaymentResponseDto;
import zw.com.organicare.service.payment.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/sales/{saleId}/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> addPayment(
            @PathVariable Long saleId,
            @RequestBody PaymentRequestDto request) {
        return ResponseEntity.ok(paymentService.addPayment(saleId, request));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getPayments(
            @PathVariable Long saleId) {
        return ResponseEntity.ok(paymentService.getPaymentsBySale(saleId));
    }
}

