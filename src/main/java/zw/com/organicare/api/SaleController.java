/**
 * @author : tadiewa
 * date: 9/18/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.dto.sale.*;
import zw.com.organicare.service.pos.sale.SaleService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
    @GetMapping("/agents/{date}")
    public ResponseEntity<List<DailyAgentSalesDto>> getAgentReport(@PathVariable LocalDate date) {
        return ResponseEntity.ok(saleService.getAgentTotals(date));
    }

    @GetMapping("/rps/{date}")
    public ResponseEntity<List<DailyRpSalesDto>> getRpReport(@PathVariable LocalDate date) {
        return ResponseEntity.ok(saleService.getRpTotals(date));
    }

    @GetMapping("/rp")
    public ResponseEntity<List<RpSaleDto>> getSalesByRpAndDateRange(
            @RequestParam String rpName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<RpSaleDto> sales = saleService.getSalesByRpAndDateRange(rpName, startDateTime, endDateTime);
        return ResponseEntity.ok(sales);
    }
}

