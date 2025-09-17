/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.dto.SalesAgentInventory;

import lombok.*;
import zw.com.organicare.constants.Branch;

import java.time.LocalDate;

//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class SalesAgentInventoryResponseDto {
//    private Long salesAgentInventoryId;
//    private String productName;
//    private int quantity;
//    private int openingStock;
//    private int stockIn;
//    private int stockOut;
//    private int numberOfProductsSold;
//    private String branch;
//    private String receivedBy;
//    private String issuedBy;
//}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesAgentInventoryResponseDto {

    private Long salesAgentInventoryId;

    // Old flat fields (for backward compatibility)
    private String productName;
    private String receivedBy;
    private String issuedBy;

    // New nested DTOs
    private ProductDto productDetails;
    private UserDto receivedByUser;
    private UserDto issuedByUser;

    private int openingStock;
    private int stockIn;
    private int numberOfProductsSold;
    private Branch branch;
    private LocalDate receivedDate;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDto {
        private Long id;
        private String name;
        private String productCode;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDto {
        private Long id;
        private String fullName;
    }
}

