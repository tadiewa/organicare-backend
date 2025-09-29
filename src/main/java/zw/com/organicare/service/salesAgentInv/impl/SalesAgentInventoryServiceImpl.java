/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.service.salesAgentInv.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.com.organicare.dto.SalesAgentInventory.SalesAgentInventoryResponseDto;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.repository.ProductRepository;
import zw.com.organicare.repository.SalesAgentInventoryRepository;
import zw.com.organicare.repository.StockOveralRepository;
import zw.com.organicare.repository.UserRepository;
import zw.com.organicare.service.salesAgentInv.SalesAgentInventoryService;

@Service
@RequiredArgsConstructor
public class SalesAgentInventoryServiceImpl implements SalesAgentInventoryService {

    private final SalesAgentInventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StockOveralRepository stockOveralRepository;




    @Override
    public Page<SalesAgentInventoryResponseDto> getStockByAgent(Long agentId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var stockList = inventoryRepository.findByReceivedBy_UserId(agentId, pageable);
        if (stockList.isEmpty()) {
            return Page.empty(pageable);
        }
        return stockList.map(inventory -> SalesAgentInventoryResponseDto.builder()
                .productName(inventory.getProduct().getName())
                .stockIn(inventory.getStockIn())
                .receivedBy(inventory.getReceivedBy().getFullName())
                .openingStock(inventory.getOpeningStock())
                .stockIn(inventory.getStockIn())
                .issuedBy(inventory.getIssuedBy().getFullName())
                .branchName(inventory.getBranch().getBranchName())
                .closingStock(inventory.getClosingStock())
                .build());
    }

    @Override
    public SalesAgentInventoryResponseDto getStockById(Long id) {
        var inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));

        return SalesAgentInventoryResponseDto.builder()
                .stockIn(inventory.getStockIn())
                .productName(inventory.getProduct().getName())
                .receivedBy(inventory.getReceivedBy().getFullName())
                .build();

    }

}




