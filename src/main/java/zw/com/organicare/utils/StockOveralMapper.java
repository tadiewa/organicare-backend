/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.utils;

import zw.com.organicare.dto.StockOveralRequest.StockOveralRequestDto;
import zw.com.organicare.dto.StockOveralRequest.StockOveralResponseDto;
import zw.com.organicare.model.Product;
import zw.com.organicare.model.StockOveral;
import zw.com.organicare.model.User;

public class StockOveralMapper {

    public static StockOveral toEntity(StockOveralRequestDto dto, Product product, User issuedBy, User receivedBy) {

        return StockOveral.builder()
                .product(product)

                .build();

    }

    public static StockOveralResponseDto toDto(StockOveral stock) {
        return StockOveralResponseDto.builder()
                .inventoryId(stock.getInventorId())
                .productName(stock.getProduct().getName())
                .numberOfProductsFreelyGiven(stock.getNumberOfProductsFreelyGiven())
                .openingStock(stock.getOpeningStock())
                .stockIn(stock.getStockIn())
                .stockOut(stock.getStockOut())
                .closingStock(stock.getClosingStock())
                .reasonForStockOut(stock.getReasonForStockOut())
                .issuedBy(stock.getIssuedBy().getFullName())
                .receivedBy(stock.getReceivedBy().getFullName())
                .branchName(stock.getBranch().getBranchName())
                .build();
    }
}

