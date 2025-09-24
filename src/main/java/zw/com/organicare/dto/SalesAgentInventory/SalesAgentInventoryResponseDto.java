/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.dto.SalesAgentInventory;

import lombok.*;
import zw.com.organicare.model.Branch;


import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesAgentInventoryResponseDto {


    private String productName;
    private String receivedBy;
    private String issuedBy;

    private int openingStock;
    private int stockIn;
    private int numberOfProductsSold;
    private Branch branch;
    private LocalDate receivedDate;


}

