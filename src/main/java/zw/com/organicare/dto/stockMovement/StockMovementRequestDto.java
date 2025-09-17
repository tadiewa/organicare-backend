/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.dto.stockMovement;

import lombok.*;
import zw.com.organicare.constants.MovementType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementRequestDto {
    private Long productId;
    private int quantity;
    private MovementType type;
    private String reason;
    private Long createdById;
}

