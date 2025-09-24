/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.dto.StockOveralRequest;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockOveralResponseDto {
    private Long inventoryId;
    private String productName;
    private Integer numberOfProductsFreelyGiven;
    private int openingStock;
    private int closingStock;
    private int stockIn;
    private int stockOut;
    private String reasonForStockOut;
    private String issuedBy;
    private String receivedBy;
    private String branchName;
}

