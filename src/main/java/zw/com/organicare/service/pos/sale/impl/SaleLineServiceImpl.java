/**
 * @author : tadiewa
 * date: 9/19/2025
 */


package zw.com.organicare.service.pos.sale.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.com.organicare.dto.sale.SaleLineRequestDto;
import zw.com.organicare.dto.sale.SaleLineResponseDto;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.model.Product;
import zw.com.organicare.model.Sale;
import zw.com.organicare.model.SaleLine;
import zw.com.organicare.repository.ProductRepository;
import zw.com.organicare.repository.SaleLineRepository;
import zw.com.organicare.repository.SaleRepository;
import zw.com.organicare.service.pos.sale.SaleLineService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleLineServiceImpl implements SaleLineService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final SaleLineRepository saleLineRepository;

    @Override
    public SaleLineResponseDto addSaleLine(Long saleId, SaleLineRequestDto request) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        BigDecimal unitPrice = product.getPrice();
        BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(request.getQuantity()));

        SaleLine saleLine = SaleLine.builder()
                .sale(sale)
                .product(product)
                .quantity(request.getQuantity())
                .unitPrice(unitPrice)
                .lineTotal(lineTotal)
                .build();

        SaleLine saved = saleLineRepository.save(saleLine);

        return new SaleLineResponseDto(
                saved.getProduct().getName(),
                saved.getQuantity(),
                saved.getUnitPrice(),
                saved.getLineTotal()
        );
    }

    @Override
    public List<SaleLineResponseDto> getSaleLinesBySale(Long saleId) {
        return saleLineRepository.findBySaleId(saleId).stream()
                .map(line -> new SaleLineResponseDto(
                        line.getProduct().getName(),
                        line.getQuantity(),
                        line.getUnitPrice(),
                        line.getLineTotal()
                ))
                .toList();
    }
}

