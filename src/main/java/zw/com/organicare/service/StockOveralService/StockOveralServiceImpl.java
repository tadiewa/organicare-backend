/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.service.StockOveralService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.com.organicare.constants.Branch;
import zw.com.organicare.constants.MovementType;
import zw.com.organicare.dto.StockOveralRequest.StockOveralRequestDto;
import zw.com.organicare.dto.StockOveralRequest.StockOveralResponseDto;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.model.*;
import zw.com.organicare.repository.*;
import zw.com.organicare.utils.StockOveralMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockOveralServiceImpl implements StockOveralService {

    private final StockOveralRepository stockOveralRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StockMovementRepository stockMovementRepository;
    private final SalesAgentInventoryRepository agentInventoryRepository;

    @Override
    public StockOveralResponseDto addStock(StockOveralRequestDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        User issuedBy = userRepository.findById(dto.getIssuedById())
                .orElseThrow(() -> new ResourceNotFoundException("IssuedBy user not found"));

        User receivedBy = userRepository.findById(dto.getReceivedById())
                .orElseThrow(() -> new ResourceNotFoundException("ReceivedBy user not found"));

        StockOveral stock = StockOveralMapper.toEntity(dto, product, issuedBy, receivedBy);

        StockOveral saved = stockOveralRepository.save(stock);
        return StockOveralMapper.toDto(saved);
    }

    @Override
    public StockOveralResponseDto getStockById(Long id) {
        StockOveral stock = stockOveralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        return StockOveralMapper.toDto(stock);
    }

    @Override
    public List<StockOveralResponseDto> getAllStock() {
        return stockOveralRepository.findAll()
                .stream()
                .map(StockOveralMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StockOveralResponseDto updateStock(Long id, StockOveralRequestDto dto) {
        StockOveral stock = stockOveralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));

        stock.setNumberOfProductsFreelyGiven(dto.getNumberOfProductsFreelyGiven());
        stock.setOpeningStock(dto.getOpeningStock());
        stock.setStockIn(dto.getStockIn());
        stock.setClosingStock(stock.getOpeningStock() + stock.getStockIn() - stock.getStockOut());
        stock.setReasonForStockOut(dto.getReasonForStockOut());

        StockOveral updated = stockOveralRepository.save(stock);
        return StockOveralMapper.toDto(updated);
    }

    @Override
    public void deleteStock(Long id) {
        StockOveral stock = stockOveralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));
        stockOveralRepository.delete(stock);
    }

    @Override
    @Transactional
    public SalesAgentInventory transferToAgent(Long productId,
                                               int quantity,
                                               Long agentId,
                                               Long issuedById,
                                               Branch branch ,
                                               MovementType movementType,
                                               String reason) {

        //  Fetch entities
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        User agent = userRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Sales agent not found"));

        User issuedBy = userRepository.findById(issuedById)
                .orElseThrow(() -> new ResourceNotFoundException("User issuing stock not found"));

        // Reduce stock in StockOveral
        StockOveral stock = stockOveralRepository.findByProduct(product)
                .orElseThrow(() -> new ResourceNotFoundException("No stock available for product"));

        if(stock.getClosingStock() < quantity){
            throw new IllegalArgumentException("Insufficient stock in warehouse");
        }

        stock.setStockOut(stock.getStockOut() + quantity);
        stock.setClosingStock(stock.getClosingStock() - quantity);
        stockOveralRepository.save(stock);

        //  Update agent inventory
        Optional<SalesAgentInventory> optionalInventory =
                agentInventoryRepository.findByReceivedByAndProduct(agent, product);
        SalesAgentInventory agentInventory = optionalInventory.orElse(new SalesAgentInventory());


        int previousClosing = (agentInventory.getSalesAgentInventoryId() != null)
                ? agentInventory.getClosingStock() : 0;

        agentInventory.setProduct(product);
        agentInventory.setReceivedBy(agent);
        agentInventory.setIssuedBy(issuedBy);
        agentInventory.setBranch(branch);
        agentInventory.setReceivedDate(LocalDate.now());
        agentInventory.setNumberOfProductsSold(agentInventory.getNumberOfProductsSold() != 0
                ? agentInventory.getNumberOfProductsSold() : 0);
        agentInventory.setNumberOfProductsFreelyGiven(agentInventory.getNumberOfProductsFreelyGiven() != 0
                ? agentInventory.getNumberOfProductsFreelyGiven() : 0);
        if(optionalInventory.isEmpty()){
        agentInventory.setClosingStock(quantity);
        agentInventory.setOpeningStock(0);
        agentInventory.setStockIn(quantity);
        }else {
            agentInventory.setClosingStock(previousClosing + quantity);
            agentInventory.setOpeningStock(previousClosing);
            agentInventory.setStockIn(agentInventory.getStockIn() + quantity);
        }

        agentInventoryRepository.save(agentInventory);

        //  Record stock movement
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setQuantity(quantity);
        movement.setType(movementType);
        movement.setReason( reason  + agent.getFullName());
        movement.setMovementDate(LocalDateTime.now());
        movement.setCreatedBy(issuedBy);
        movement.setRequestBy(agent);

        stockMovementRepository.save(movement);

        return agentInventory;
    }
}


