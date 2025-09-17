/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.dto.stockMovement;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementResponseDto {
    private Long id;
    private String productName;
    private int quantity;
    private String type;
    private String reason;
    private LocalDateTime movementDate;
    private String createdByName;
}
