/**
 * @author : tadiewa
 * date: 9/18/2025
 */


package zw.com.organicare.utils;

import zw.com.organicare.dto.payment.PaymentResponseDto;
import zw.com.organicare.dto.sale.SaleLineResponseDto;
import zw.com.organicare.dto.sale.SaleResponseDto;
import zw.com.organicare.model.Sale;


public class SaleMapper {

    public static SaleResponseDto toDto(Sale sale) {
        SaleResponseDto dto = SaleResponseDto.builder()
                .saleId(sale.getId())
                .patientId(sale.getPatient() != null ? sale.getPatient().getPatientId() : null)
                .patientName(sale.getPatient() != null ? sale.getPatient().getFullName() : null)
                .totalAmountDue(sale.getTotalAmountDue())
                .totalPaid(sale.getTotalPaid())
                .changeGiven(sale.getChangeGiven())
                .uncollectedChange(sale.getUncollectedChange())
                .saleDate(sale.getSaleDate())
                .agentId(sale.getAgent() != null ? sale.getAgent().getUserId() : null)
                .build();

        dto.setSaleLines( sale.getSaleLines().stream().map(sl ->
                SaleLineResponseDto.builder()
                        .productName(sl.getProduct().getName())
                        .quantity(sl.getQuantity())
                        .unitPrice(sl.getUnitPrice())
                        .lineTotal(sl.getLineTotal())
                        .build()
        ).toList());

        dto.setPayments( sale.getPayments().stream().map(p ->
                PaymentResponseDto.builder()
                        .paymentType(p.getPaymentType().getName())
                        .amount(p.getAmount())
                        .build()
        ).toList());

        return dto;
    }

}

