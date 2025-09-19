/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.dto.sale;

import lombok.*;
import zw.com.organicare.dto.payment.PaymentResponseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleResponseDto {
    private Long saleId;
    private Long patientId;
    private String patientName;
    private BigDecimal totalAmountDue;
    private BigDecimal totalPaid;
    private BigDecimal changeGiven;
    private BigDecimal uncollectedChange;
    private LocalDateTime saleDate;
    private Long agentId;
    private List<SaleLineResponseDto> saleLines;
    private List<PaymentResponseDto> payments;
}
