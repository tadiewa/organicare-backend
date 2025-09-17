/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.utils;

import zw.com.organicare.constants.MovementType;
import zw.com.organicare.dto.stockMovement.StockMovementRequestDto;
import zw.com.organicare.dto.stockMovement.StockMovementResponseDto;
import zw.com.organicare.model.Product;
import zw.com.organicare.model.StockMovement;
import zw.com.organicare.model.User;

import java.time.LocalDateTime;

public class StockMovementMapper {

    public static StockMovement toEntity(StockMovementRequestDto dto, Product product, User user) {
        return StockMovement.builder()
                .product(product)
                .quantity(dto.getQuantity())
                .type(dto.getType())
                .reason(dto.getReason())
                .movementDate(LocalDateTime.now())
                .createdBy(user)
                .build();
    }

    public static StockMovementResponseDto toDto(StockMovement entity) {
        return StockMovementResponseDto.builder()
                .id(entity.getId())
                .productName(entity.getProduct().getName())
                .quantity(entity.getQuantity())
                .type(entity.getType().name())
                .reason(entity.getReason())
                .movementDate(entity.getMovementDate())
                .createdByName(entity.getCreatedBy().getFullName())
                .build();
    }
}

