/**
 * @author : tadiewa
 * date: 9/17/2025
 */

package zw.com.organicare.service.salesAgentInv;

import zw.com.organicare.dto.SalesAgentInventory.SalesAgentInventoryRequestDto;
import zw.com.organicare.dto.SalesAgentInventory.SalesAgentInventoryResponseDto;

import java.util.List;

public interface SalesAgentInventoryService {
    SalesAgentInventoryResponseDto assignOrUpdateStock(SalesAgentInventoryRequestDto dto);

    List<SalesAgentInventoryResponseDto> getStockByAgent(Long agentId);

    SalesAgentInventoryResponseDto getStockById(Long id);

    SalesAgentInventoryResponseDto transferFromStockOveral(SalesAgentInventoryRequestDto dto);
}
