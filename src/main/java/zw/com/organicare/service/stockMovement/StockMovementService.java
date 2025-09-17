/**
 * @author : tadiewa
 * date: 9/17/2025
 */

package zw.com.organicare.service.stockMovement;

import zw.com.organicare.dto.stockMovement.StockMovementRequestDto;
import zw.com.organicare.dto.stockMovement.StockMovementResponseDto;
import zw.com.organicare.dto.stockMovement.StockReportDto;

import java.time.LocalDate;
import java.util.List;

public interface StockMovementService {
    StockMovementResponseDto recordMovement(StockMovementRequestDto dto);
    List<StockMovementResponseDto> getMovementsForProduct(Long productId, LocalDate start, LocalDate end);
    StockReportDto generateReport(LocalDate start, LocalDate end);
}
