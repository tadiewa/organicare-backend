/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.service.product.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zw.com.organicare.dto.product.ProductRequestDto;
import zw.com.organicare.dto.product.ProductResponseDto;
import zw.com.organicare.exception.AlreadyExistsException;
import zw.com.organicare.model.Product;
import zw.com.organicare.repository.ProductRepository;
import zw.com.organicare.service.product.ProductService;
import zw.com.organicare.utils.ProductCodeGenerator;
import zw.com.organicare.utils.ProductMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCodeGenerator codeGenerator;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        productRepository.findByName(dto.getName()).ifPresent(p -> {
            throw new AlreadyExistsException("Product with name " + dto.getName() + " already exists");
        });
        Product product = ProductMapper.toEntity(dto);

        // Always generate a unique product code
        product.setProductCode(generateUniqueProductCode());

        Product saved = productRepository.save(product);
        return ProductMapper.toResponseDto(saved);
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductMapper.toResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setActive(dto.isActive());

        Product updated = productRepository.save(product);
        return ProductMapper.toResponseDto(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    private String generateUniqueProductCode() {
        String code;
        do {
            code = codeGenerator.generate();
        } while (productRepository.existsByProductCode(code));
        return code;
    }
}
