/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.dto.SalesAgentInventory.SalesAgentInventoryRequestDto;
import zw.com.organicare.dto.SalesAgentInventory.SalesAgentInventoryResponseDto;
import zw.com.organicare.service.salesAgentInv.SalesAgentInventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/sales-agent-inventory")
@RequiredArgsConstructor
public class SalesAgentInventoryController {

    private final SalesAgentInventoryService inventoryService;

    // Transfer from StockOveral to Agent
    @PostMapping("/transfer")
    public ResponseEntity<SalesAgentInventoryResponseDto> transferStock(@RequestBody SalesAgentInventoryRequestDto dto) {
        return ResponseEntity.ok(inventoryService.transferFromStockOveral(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesAgentInventoryResponseDto> getStock(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getStockById(id));
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<SalesAgentInventoryResponseDto>> getStockByAgent(@PathVariable Long agentId) {
        return ResponseEntity.ok(inventoryService.getStockByAgent(agentId));
    }
}

