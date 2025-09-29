/**
 * @author : tadiewa
 * date: 9/17/2025
 */

package zw.com.organicare.service.salesAgentInv;

import org.springframework.data.domain.Page;

import zw.com.organicare.dto.SalesAgentInventory.SalesAgentInventoryResponseDto;


public interface SalesAgentInventoryService {

    Page<SalesAgentInventoryResponseDto> getStockByAgent(Long agentId,int page, int size);

    SalesAgentInventoryResponseDto getStockById(Long id);

}
