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
        StockOveral stock = StockOveral.builder()
                .product(product)
                .numberOfProductsFreelyGiven(dto.getNumberOfProductsFreelyGiven())
                .openingStock(dto.getOpeningStock())
                .stockIn(dto.getStockIn())
                .stockOut(0)
                .closingStock(dto.getOpeningStock() + dto.getStockIn())
                .reasonForStockOut(dto.getReasonForStockOut())
                .issuedBy(issuedBy)
                .receivedBy(receivedBy)
                .build();
        return stock;
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
                .build();
    }
}

