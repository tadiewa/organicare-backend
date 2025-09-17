/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.utils;

import zw.com.organicare.constants.Branch;
import zw.com.organicare.dto.SalesAgentInventory.SalesAgentInventoryRequestDto;
import zw.com.organicare.dto.SalesAgentInventory.SalesAgentInventoryResponseDto;
import zw.com.organicare.model.Product;
import zw.com.organicare.model.SalesAgentInventory;
import zw.com.organicare.model.User;

import java.time.LocalDate;

//public class SalesAgentInventoryMapper {
//
//    private SalesAgentInventoryMapper() {
//        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
//    }
//
//    public static SalesAgentInventory toEntity(SalesAgentInventoryRequestDto dto,
//                                               Product product,
//                                               User receivedBy,
//                                               User issuedBy) {
//         return SalesAgentInventory.builder()
//                .product(product)
//                .openingStock(0)
//                .stockIn(dto.getStockIn())
//                 .receivedDate(LocalDate.now())
//                .numberOfProductsSold(0)
//                .branch(Branch.valueOf(dto.getBranch()))
//                .numberOfProductsFreelyGiven(dto.getNumberOfProductsFreelyGiven())
//                .receivedBy(receivedBy)
//                .issuedBy(issuedBy)
//                .build();
//    }
//
//    public static SalesAgentInventoryResponseDto toDto(SalesAgentInventory inventory) {
//        return SalesAgentInventoryResponseDto.builder()
//                .salesAgentInventoryId(inventory.getSalesAgentInventoryId())
//                .productName(inventory.getProduct().getName())
//                .openingStock(inventory.getOpeningStock())
//                .stockIn(inventory.getStockIn())
//                .numberOfProductsSold(inventory.getNumberOfProductsSold())
//                .branch(inventory.getBranch())
//                .receivedBy(inventory.getReceivedBy().getFullName())
//                .issuedBy(inventory.getIssuedBy().getFullName())
//                .build();
//    }



public class SalesAgentInventoryMapper {

    private SalesAgentInventoryMapper() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }

    public static SalesAgentInventory toEntity(SalesAgentInventoryRequestDto dto,
                                               Product product,
                                               User receivedBy,
                                               User issuedBy) {
        return SalesAgentInventory.builder()
                .product(product)
                .openingStock(0)
                .stockIn(dto.getStockIn())
                .receivedDate(LocalDate.now())
                .numberOfProductsSold(0)
                .branch(Branch.valueOf(dto.getBranch()))
                .numberOfProductsFreelyGiven(dto.getNumberOfProductsFreelyGiven())
                .receivedBy(receivedBy)
                .issuedBy(issuedBy)
                .build();
    }

    public static SalesAgentInventoryResponseDto toDto(SalesAgentInventory inventory) {
        if (inventory == null) return null;

        SalesAgentInventoryResponseDto dto = new SalesAgentInventoryResponseDto();

        // Old flat fields
        dto.setProductName(inventory.getProduct().getName());
        dto.setReceivedBy(inventory.getReceivedBy().getFullName());
        dto.setIssuedBy(inventory.getIssuedBy().getFullName());

        // New nested DTOs
        dto.setProductDetails(new SalesAgentInventoryResponseDto.ProductDto(
                inventory.getProduct().getProductId(),
                inventory.getProduct().getName(),
                inventory.getProduct().getProductCode()
        ));

        dto.setReceivedByUser(new SalesAgentInventoryResponseDto.UserDto(
                inventory.getReceivedBy().getUserId(),
                inventory.getReceivedBy().getFullName()
        ));

        dto.setIssuedByUser(new SalesAgentInventoryResponseDto.UserDto(
                inventory.getIssuedBy().getUserId(),
                inventory.getIssuedBy().getFullName()
        ));

        // Stock info
        dto.setOpeningStock(inventory.getOpeningStock());
        dto.setStockIn(inventory.getStockIn());
        dto.setNumberOfProductsSold(inventory.getNumberOfProductsSold());
        dto.setBranch(inventory.getBranch());
        dto.setReceivedDate(inventory.getReceivedDate());

        return dto;
    }
}

//}

