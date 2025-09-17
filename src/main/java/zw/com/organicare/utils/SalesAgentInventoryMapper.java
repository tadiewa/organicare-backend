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
                .numberOfProductsSold(0)
                .branch(Branch.valueOf(dto.getBranch()))
                .numberOfProductsFreelyGiven(dto.getNumberOfProductsFreelyGiven())
                .receivedBy(receivedBy)
                .issuedBy(issuedBy)
                .build();
    }

    public static SalesAgentInventoryResponseDto toDto(SalesAgentInventory inventory) {
        return SalesAgentInventoryResponseDto.builder()
                .salesAgentInventoryId(inventory.getSalesAgentInventoryId())
                .productName(inventory.getProduct().getName())
                .openingStock(inventory.getOpeningStock())
                .stockIn(inventory.getStockIn())
                .numberOfProductsSold(inventory.getNumberOfProductsSold())
                .branch(inventory.getBranch().toString())
                .receivedBy(inventory.getReceivedBy().getFullName())
                .issuedBy(inventory.getIssuedBy().getFullName())
                .build();
    }
}

