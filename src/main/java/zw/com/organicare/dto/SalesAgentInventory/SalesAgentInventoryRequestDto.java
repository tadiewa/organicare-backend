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
public class SalesAgentInventoryRequestDto {
    private Long productId;
    private int  stockIn; // Stock assigned to agent
    private Long receivedById; // Sales agent
    private Long issuedById;   // User who issued from warehouse
    private Integer numberOfProductsFreelyGiven;
    private String branch;
}
