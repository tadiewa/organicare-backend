/**
 * @author : tadiewa
 * date: 9/19/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.dto.sale.SaleLineRequestDto;
import zw.com.organicare.dto.sale.SaleLineResponseDto;
import zw.com.organicare.service.pos.sale.SaleLineService;

import java.util.List;

@RestController
@RequestMapping("/api/sales/{saleId}/lines")
@RequiredArgsConstructor
public class SaleLineController {

    private final SaleLineService saleLineService;

    @PostMapping
    public ResponseEntity<SaleLineResponseDto> addSaleLine(
            @PathVariable Long saleId,
            @RequestBody SaleLineRequestDto request) {
        return ResponseEntity.ok(saleLineService.addSaleLine(saleId, request));
    }

    @GetMapping
    public ResponseEntity<List<SaleLineResponseDto>> getSaleLines(
            @PathVariable Long saleId) {
        return ResponseEntity.ok(saleLineService.getSaleLinesBySale(saleId));
    }
}

