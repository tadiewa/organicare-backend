/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.dto.StockOveralRequest;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockOveralRequestDto {
    private Long productId;
    private int stockIn;
    private String reason;
    private Long issuedById;
}
