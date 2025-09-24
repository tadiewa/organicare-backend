/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.service.salesAgentInv.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.com.organicare.constants.Branch;
import zw.com.organicare.dto.SalesAgentInventory.SalesAgentInventoryRequestDto;
import zw.com.organicare.dto.SalesAgentInventory.SalesAgentInventoryResponseDto;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.model.Product;
import zw.com.organicare.model.SalesAgentInventory;
import zw.com.organicare.model.StockOveral;
import zw.com.organicare.model.User;
import zw.com.organicare.repository.ProductRepository;
import zw.com.organicare.repository.SalesAgentInventoryRepository;
import zw.com.organicare.repository.StockOveralRepository;
import zw.com.organicare.repository.UserRepository;
import zw.com.organicare.service.salesAgentInv.SalesAgentInventoryService;


import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SalesAgentInventoryServiceImpl implements SalesAgentInventoryService {

    private final SalesAgentInventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StockOveralRepository stockOveralRepository;


//    @Override
//    @Transactional
//    public SalesAgentInventoryResponseDto assignOrUpdateStock(SalesAgentInventoryRequestDto dto) {
//        // This now only updates agent inventory if stock already exists
//        Product product = productRepository.findById(dto.getProductId())
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        User receivedBy = userRepository.findById(dto.getReceivedById())
//                .orElseThrow(() -> new RuntimeException("Sales agent not found"));
//
//        User issuedBy = userRepository.findById(dto.getIssuedById())
//                .orElseThrow(() -> new RuntimeException("Issuer not found"));
//
//        SalesAgentInventory inventory = inventoryRepository
//                .findByReceivedBy_UserIdAndProduct_ProductId(receivedBy.getUserId(), product.getProductId())
//                .orElse(null);
//
//        if (inventory != null) {
//            // Already has stock → update
//            inventory.setOpeningStock(inventory.getClosingStock());
//            inventory.setStockIn(inventory.getStockIn() + dto.getStockIn());
//            inventory.setClosingStock(inventory.getOpeningStock() + inventory.getStockIn() - inventory.getNumberOfProductsSold());
//            inventory.setReceivedDate(LocalDate.now());
//
//        } else {
//            // First-time assignment
//            inventory = SalesAgentInventoryMapper.toEntity(dto, product, receivedBy, issuedBy);
//        }
//
//        SalesAgentInventory saved = inventoryRepository.save(inventory);
//        return SalesAgentInventoryMapper.toDto(saved);
//    }

    @Override
    public SalesAgentInventoryResponseDto assignOrUpdateStock(SalesAgentInventoryRequestDto dto) {
        return null;
    }

    @Override
    public List<SalesAgentInventoryResponseDto> getStockByAgent(Long agentId) {
        return List.of();
    }

    @Override
    public SalesAgentInventoryResponseDto getStockById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public SalesAgentInventoryResponseDto transferFromStockOveral(SalesAgentInventoryRequestDto dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Get warehouse stock
        StockOveral stockOveral = stockOveralRepository.findByProduct_ProductId(product.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("StockOveral not found for product"));

        //  Check if enough stock
        if (dto.getStockIn() > stockOveral.getClosingStock()) {
            throw new ResourceNotFoundException("Not enough stock in warehouse");
        }

        // Deduct from StockOveral
        stockOveral.setStockOut(stockOveral.getStockOut() + dto.getStockIn());
        stockOveral.setClosingStock(stockOveral.getOpeningStock() + stockOveral.getStockIn() - stockOveral.getStockOut()- stockOveral.getNumberOfProductsFreelyGiven());
        stockOveralRepository.save(stockOveral);

        //  Assign to sales agent
        return assignOrUpdateStock(dto);
    }

//    @Override
//    public List<SalesAgentInventoryResponseDto> getStockByAgent(Long agentId) {
//      var stockList  = inventoryRepository.findByReceivedBy_UserId(agentId);
//       return stockList.stream()
//               .toList();
    }

//    @Override
//    public SalesAgentInventoryResponseDto getStockById(Long id) {
//        SalesAgentInventory inventory = inventoryRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Stock not found"));
//        return SalesAgentInventoryMapper.toDto(inventory);
//    }


