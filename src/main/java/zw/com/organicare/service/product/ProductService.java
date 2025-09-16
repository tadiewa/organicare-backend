/**
 * @author : tadiewa
 * date: 9/16/2025
 */

package zw.com.organicare.service.product;

import zw.com.organicare.dto.product.ProductRequestDto;
import zw.com.organicare.dto.product.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto dto);
    ProductResponseDto getProductById(Long id);
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto updateProduct(Long id, ProductRequestDto dto);
    void deleteProduct(Long id);

}
