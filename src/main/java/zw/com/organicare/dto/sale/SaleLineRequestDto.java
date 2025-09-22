/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.dto.sale;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleLineRequestDto {
    private Long productId;
    private int quantity;
//    private BigDecimal unitPrice;
//    private BigDecimal lineTotal;
}
