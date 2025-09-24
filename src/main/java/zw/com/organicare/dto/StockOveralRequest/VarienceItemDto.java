/**
 * @author : tadiewa
 * date: 9/24/2025
 */


package zw.com.organicare.dto.StockOveralRequest;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VarienceItemDto {
    private Long productId;
    private Double quantity;
}
