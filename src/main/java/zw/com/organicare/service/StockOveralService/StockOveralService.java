/**
 * @author : tadiewa
 * date: 9/16/2025
 */

package zw.com.organicare.service.StockOveralService;

import zw.com.organicare.dto.StockOveralRequest.StockOveralRequestDto;
import zw.com.organicare.dto.StockOveralRequest.StockOveralResponseDto;

import java.util.List;

public interface StockOveralService {
    StockOveralResponseDto addStock(StockOveralRequestDto dto);
    StockOveralResponseDto getStockById(Long id);
    List<StockOveralResponseDto> getAllStock();
    StockOveralResponseDto updateStock(Long id, StockOveralRequestDto dto);
    void deleteStock(Long id);
}
