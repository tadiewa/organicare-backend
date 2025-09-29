/**
 * @author : tadiewa
 * date: 9/18/2025
 */

package zw.com.organicare.service.pos.sale;

import zw.com.organicare.dto.sale.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleService {
    SaleResponseDto createSale(SaleRequestDto request);
    SaleResponseDto getSale(Long saleId);
    List<DailyAgentSalesDto> getAgentTotals(LocalDate date) ;
    List<DailyRpSalesDto> getRpTotals(LocalDate date);
    List<RpSaleDto> getSalesByRpAndDateRange(String rpName, LocalDateTime start, LocalDateTime end);
}
