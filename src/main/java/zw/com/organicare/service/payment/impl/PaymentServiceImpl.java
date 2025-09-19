/**
 * @author : tadiewa
 * date: 9/19/2025
 */


package zw.com.organicare.service.payment.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.com.organicare.dto.payment.PaymentRequestDto;
import zw.com.organicare.dto.payment.PaymentResponseDto;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.model.Payment;
import zw.com.organicare.model.PaymentType;
import zw.com.organicare.model.Sale;
import zw.com.organicare.repository.PaymentRepository;
import zw.com.organicare.repository.PaymentTypeRepository;
import zw.com.organicare.repository.SaleRepository;
import zw.com.organicare.service.payment.PaymentService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final SaleRepository saleRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    @Override
    public PaymentResponseDto addPayment(Long saleId, PaymentRequestDto request) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        PaymentType type = paymentTypeRepository.findById(request.getPaymentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("PaymentType not found"));

        Payment payment = Payment.builder()
                .sale(sale)
                .paymentType(type)
                .amount(request.getAmount())
                .paymentDate(LocalDateTime.now())
                .build();

        Payment saved = paymentRepository.save(payment);

        return new PaymentResponseDto(
                saved.getPaymentType().getName(),
                saved.getAmount()
        );
    }

    @Override
    public List<PaymentResponseDto> getPaymentsBySale(Long saleId) {
        return paymentRepository.findById(saleId).stream()
                .map(p -> new PaymentResponseDto(
                        p.getPaymentType().getName(),
                        p.getAmount()
                ))
                .toList();
    }
}

