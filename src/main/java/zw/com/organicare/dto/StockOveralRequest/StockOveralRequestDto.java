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
    private Integer numberOfProductsFreelyGiven;
    private int openingStock;
    private int stockIn;
    private String reasonForStockOut;
    private Long issuedById;
    private Long receivedById;
}
