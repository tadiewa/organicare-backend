/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.dto.payment;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {
    private  Long  paymentTypeId;
    //private String paymentType;
    private BigDecimal amount;
}
