/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.service.StockOveralService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.com.organicare.constants.Branch;
import zw.com.organicare.constants.MovementType;
import zw.com.organicare.dto.SalesAgentInventory.SalesAgentInventoryResponseDto;
import zw.com.organicare.dto.StockOveralRequest.*;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.exception.UserNotFound;
import zw.com.organicare.model.*;
import zw.com.organicare.repository.*;
import zw.com.organicare.service.authService.AuthService;
import zw.com.organicare.utils.StockOveralMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockOveralServiceImpl implements StockOveralService {

    private final StockOveralRepository stockOveralRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StockMovementRepository stockMovementRepository;
    private final SalesAgentInventoryRepository agentInventoryRepository;
    private final AuthService authService;

    @Override
    @Transactional
    public StockOveralResponseDto addStock(StockOveralRequestDto dto) {

        var currentUser  = authService.getAuthenticatedUser();
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        User receivedFrom = userRepository.findById(dto.getIssuedById())
                .orElseThrow(() -> new UserNotFound("Issuer  user not found"));


       Optional <StockOveral>  optionalexistingStock = stockOveralRepository.findByProduct(product);
         StockOveral existingStock = optionalexistingStock.orElse(new StockOveral());

         if(optionalexistingStock.isEmpty()) {
             existingStock.setProduct(product);
             existingStock.setNumberOfProductsFreelyGiven(0);
             existingStock.setOpeningStock(0);
             existingStock.setStockIn(dto.getStockIn());
             existingStock.setStockOut(0);
             existingStock.setClosingStock(dto.getStockIn());
             existingStock.setReasonForStockOut(null);
             existingStock.setIssuedBy(receivedFrom);
             existingStock.setReceivedBy(currentUser);
             existingStock.setDateCreated(LocalDate.now());
             existingStock.setBranch(currentUser.getBranch());

         } else {

             existingStock.setOpeningStock(existingStock.getClosingStock());
             existingStock.setStockIn(dto.getStockIn());
             existingStock.setClosingStock(existingStock.getClosingStock() + dto.getStockIn());
             existingStock.setIssuedBy(receivedFrom);
             existingStock.setReceivedBy(currentUser);
             existingStock.setReasonForStockOut(dto.getReason());
             existingStock.setDateCreated(LocalDate.now());
             existingStock.setBranch(currentUser.getBranch());
             existingStock.setProduct(product);
             existingStock.setStockOut(existingStock.getStockOut());
             existingStock.setNumberOfProductsFreelyGiven(existingStock.getNumberOfProductsFreelyGiven());

         }

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setQuantity(dto.getStockIn());
        movement.setType(MovementType.IN);
        movement.setReason(dto.getReason()  + currentUser.getFullName());
        movement.setMovementDate(LocalDateTime.now());
        // issuer is the one who created the stock
        movement.setCreatedBy(receivedFrom);
        // receiver is the current user
        movement.setRequestBy(currentUser);
        movement.setBranch(currentUser.getBranch());
        movement.setMovementDate(LocalDateTime.now());
    log.info("Saving stock movement:------------------------->{}", movement);
        stockMovementRepository.save(movement);




        log.info("Saving stock overal:------------------------->{}", existingStock);


        StockOveral saved = stockOveralRepository.save(existingStock);
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
    @Transactional
    public StockOveralResponseDto updateStock(Long id, StockOveralRequestDto dto) {
        StockOveral stock = stockOveralRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found"));

        stock.setStockIn(dto.getStockIn());
        stock.setClosingStock(stock.getClosingStock() + stock.getStockIn());

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
    public SalesAgentInventoryResponseDto transferToAgent(TransferRequest request) {

        var currentUser  = authService.getAuthenticatedUser();
        User agent = userRepository.findById(request.getAgentId())
                .orElseThrow(() -> new ResourceNotFoundException("Sales agent not found"));
        SalesAgentInventoryResponseDto response = null;

        for( VarienceItemDto item : request.getItems()){

        //  Fetch entities
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));


        // Reduce stock in StockOveral
        StockOveral stock = stockOveralRepository.findByProduct(product)
                .orElseThrow(() -> new ResourceNotFoundException("No stock available for product"));

        if(stock.getClosingStock() <item.getQuantity()){
            throw new IllegalArgumentException("Insufficient stock in warehouse");
        }

        stock.setStockOut(stock.getStockOut() + item.getQuantity().intValue());
        stock.setClosingStock(stock.getClosingStock() - item.getQuantity().intValue());
        stockOveralRepository.save(stock);

        //  Update agent inventory
        Optional<SalesAgentInventory> optionalInventory =
                agentInventoryRepository.findByReceivedByAndProduct(agent, product);
        SalesAgentInventory agentInventory = optionalInventory.orElse(new SalesAgentInventory());


        int previousClosing = (agentInventory.getSalesAgentInventoryId() != null)
                ? agentInventory.getClosingStock() : 0;

        agentInventory.setProduct(product);
        agentInventory.setReceivedBy(agent);
        agentInventory.setIssuedBy(currentUser);
        agentInventory.setBranch(currentUser.getBranch());
        agentInventory.setReceivedDate(LocalDate.now());
        agentInventory.setNumberOfProductsSold(agentInventory.getNumberOfProductsSold() != 0
                ? agentInventory.getNumberOfProductsSold() : 0);
        agentInventory.setNumberOfProductsFreelyGiven(agentInventory.getNumberOfProductsFreelyGiven() != 0
                ? agentInventory.getNumberOfProductsFreelyGiven() : 0);
        if(optionalInventory.isEmpty()){
        agentInventory.setClosingStock(item.getQuantity().intValue());
        agentInventory.setOpeningStock(0);
        agentInventory.setStockIn(item.getQuantity().intValue());
        }else {
            agentInventory.setClosingStock(previousClosing + item.getQuantity().intValue());
            agentInventory.setOpeningStock(previousClosing);
            agentInventory.setStockIn(agentInventory.getStockIn() + item.getQuantity().intValue());
        }

        agentInventoryRepository.save(agentInventory);

        //  Record stock movement
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setQuantity(item.getQuantity().intValue());
        movement.setType(MovementType.IN);
        movement.setReason(request.getReason() + " " + agent.getFullName());
        movement.setMovementDate(LocalDateTime.now());
        movement.setCreatedBy(currentUser);
        movement.setRequestBy(agent);

        stockMovementRepository.save(movement);
        response = SalesAgentInventoryResponseDto.builder()
                .productName(product.getName())
                .receivedBy(agent.getFullName())
                .issuedBy(currentUser.getFullName())
                .stockIn(item.getQuantity().intValue())
                .build();
        }
        return response ;
    }

    @Override
    @Transactional
    public StockOveralResponseDto handleVarience(VarienceRequestDto request) {

        var currentUser = authService.getAuthenticatedUser();

        StockOveralResponseDto lastResponse = null;
        User agent = userRepository.findById(request.getReceivedById())
                .orElseThrow(() -> new ResourceNotFoundException("Sales agent not found"));

        for (VarienceItemDto item : request.getItems()) {
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Reduce stock in StockOveral
        StockOveral stock = stockOveralRepository.findByProduct(product)
                .orElseThrow(() -> new ResourceNotFoundException("No stock available for product"));

        if (stock.getClosingStock() < item.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock in warehouse");
        }

        stock.setNumberOfProductsFreelyGiven(
                stock.getNumberOfProductsFreelyGiven() + item.getQuantity().intValue());
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setQuantity(item.getQuantity().intValue());
        movement.setType(MovementType.RETURN);
        movement.setReason(request.getReason() + agent.getFullName());
        movement.setMovementDate(LocalDateTime.now());
        movement.setCreatedBy(currentUser);
        movement.setRequestBy(agent);
        movement.setBranch(currentUser.getBranch());

        stockMovementRepository.save(movement);
        stock.setStockOut(stock.getStockOut() + item.getQuantity().intValue());
        stock.setClosingStock(stock.getClosingStock() - item.getQuantity().intValue());
        stock.setDateCreated(LocalDate.now());
        stockOveralRepository.save(stock);

            lastResponse = StockOveralResponseDto.builder()
                .closingStock(stock.getClosingStock())
                .productName(product.getName())
                .numberOfProductsFreelyGiven(item.getQuantity().intValue())
                .receivedBy(agent.getFullName())
                .issuedBy(currentUser.getFullName())
                .reasonForStockOut(request.getReason())
                .build();

    }
        return lastResponse;
    }
}


