/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.constants.Branch;
import zw.com.organicare.constants.MovementType;
import zw.com.organicare.dto.SalesAgentInventory.SalesAgentInventoryResponseDto;
import zw.com.organicare.dto.StockOveralRequest.StockOveralRequestDto;
import zw.com.organicare.dto.StockOveralRequest.StockOveralResponseDto;
import zw.com.organicare.dto.StockOveralRequest.TransferRequest;
import zw.com.organicare.dto.StockOveralRequest.VarienceRequestDto;
import zw.com.organicare.model.SalesAgentInventory;
import zw.com.organicare.service.StockOveralService.StockOveralService;


import java.util.List;

@RestController
@RequestMapping("/api/stock-overal")
@RequiredArgsConstructor
public class StockOveralController {

    private final StockOveralService stockOveralService;

    @PostMapping
    public ResponseEntity<StockOveralResponseDto> addStock(@RequestBody StockOveralRequestDto dto) {
        return ResponseEntity.ok(stockOveralService.addStock(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockOveralResponseDto> getStock(@PathVariable Long id) {
        return ResponseEntity.ok(stockOveralService.getStockById(id));
    }

    @GetMapping
    public ResponseEntity<List<StockOveralResponseDto>> getAllStock() {
        return ResponseEntity.ok(stockOveralService.getAllStock());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockOveralResponseDto> updateStock(@PathVariable Long id,
                                                              @RequestBody StockOveralRequestDto dto) {
        return ResponseEntity.ok(stockOveralService.updateStock(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockOveralService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<SalesAgentInventoryResponseDto> transferToAgent(@RequestBody TransferRequest request) {

        SalesAgentInventoryResponseDto inventory = stockOveralService.transferToAgent(request);
        return ResponseEntity.ok(inventory);
    }


    @PostMapping("/varience")
    public ResponseEntity<StockOveralResponseDto> varience (@RequestBody VarienceRequestDto request){
        StockOveralResponseDto response = stockOveralService.handleVarience(request);
        return ResponseEntity.ok(response);
    }
}