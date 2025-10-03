/**
 * @author : tadiewa
 * date: 10/3/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zw.com.organicare.dto.finance.DailyCashflowDto;
import zw.com.organicare.service.cashflow.CashflowService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/cashflow")
@RequiredArgsConstructor
public class CashflowController {
    private final CashflowService cashflowService;

    @GetMapping("/daily")
    public ResponseEntity<DailyCashflowDto> getDailyCashflow(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(cashflowService.generateDailyCashflow(date));
    }
}
