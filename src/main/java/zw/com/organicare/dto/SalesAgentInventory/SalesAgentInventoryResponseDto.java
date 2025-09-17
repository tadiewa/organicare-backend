/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.dto.SalesAgentInventory;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesAgentInventoryResponseDto {
    private Long salesAgentInventoryId;
    private String productName;
    private int quantity;
    private int openingStock;
    private int stockIn;
    private int stockOut;
    private int numberOfProductsSold;
    private String branch;
    private String receivedBy;
    private String issuedBy;
}
