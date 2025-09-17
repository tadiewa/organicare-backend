/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.dto.stockMovement.StockMovementRequestDto;
import zw.com.organicare.dto.stockMovement.StockMovementResponseDto;
import zw.com.organicare.dto.stockMovement.StockReportDto;
import zw.com.organicare.service.stockMovement.StockMovementService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping
    public ResponseEntity<StockMovementResponseDto> recordMovement(@RequestBody StockMovementRequestDto dto) {
        return ResponseEntity.ok(stockMovementService.recordMovement(dto));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<StockMovementResponseDto>> getMovementsForProduct(
            @PathVariable Long productId,
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        return ResponseEntity.ok(stockMovementService.getMovementsForProduct(productId, start, end));
    }

    @GetMapping("/report")
    public ResponseEntity<StockReportDto> generateReport(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        return ResponseEntity.ok(stockMovementService.generateReport(start, end));
    }
}

