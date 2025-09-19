/**
 * @author : tadiewa
 * date: 9/18/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.dto.sale.SaleRequestDto;
import zw.com.organicare.dto.sale.SaleResponseDto;
import zw.com.organicare.service.pos.sale.SaleService;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponseDto> createSale(@RequestBody SaleRequestDto request) {
        SaleResponseDto response = saleService.createSale(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDto> getSale(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSale(id));
    }
}

