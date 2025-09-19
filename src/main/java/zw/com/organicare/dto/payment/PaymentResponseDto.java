/**
 * @author : tadiewa
 * date: 9/18/2025
 */


package zw.com.organicare.dto.payment;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {
    private String paymentType;
    private BigDecimal amount;

}
