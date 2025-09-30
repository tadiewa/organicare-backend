/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.utils;

import zw.com.organicare.dto.product.ProductRequestDto;
import zw.com.organicare.dto.product.ProductResponseDto;
import zw.com.organicare.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .isActive(dto.isActive())
                .isCos(dto.isCos())
                .build();
    }

    public static ProductResponseDto toResponseDto(Product product) {
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .productCode(product.getProductCode())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .isActive(product.isActive())
                .isCos(product.isCos())
                .build();
    }
}

