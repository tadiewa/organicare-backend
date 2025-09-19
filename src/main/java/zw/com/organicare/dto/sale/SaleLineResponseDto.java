/**
 * @author : tadiewa
 * date: 9/18/2025
 */


package zw.com.organicare.dto.sale;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaleLineResponseDto {
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;
}
