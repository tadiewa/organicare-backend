/**
 * @author : tadiewa
 * date: 9/16/2025
 */

package zw.com.organicare.service.StockOveralService;

import zw.com.organicare.constants.Branch;
import zw.com.organicare.constants.MovementType;
import zw.com.organicare.dto.StockOveralRequest.StockOveralRequestDto;
import zw.com.organicare.dto.StockOveralRequest.StockOveralResponseDto;
import zw.com.organicare.dto.StockOveralRequest.VarienceRequestDto;
import zw.com.organicare.model.SalesAgentInventory;

import java.util.List;

public interface StockOveralService {
    StockOveralResponseDto addStock(StockOveralRequestDto dto);
    StockOveralResponseDto getStockById(Long id);
    List<StockOveralResponseDto> getAllStock();
    StockOveralResponseDto updateStock(Long id, StockOveralRequestDto dto);
    void deleteStock(Long id);
    SalesAgentInventory transferToAgent(Long productId, int quantity, Long agentId, Long issuedById, Branch branch, MovementType movementType, String reason);
    StockOveralResponseDto handleVarience(VarienceRequestDto request);
}
