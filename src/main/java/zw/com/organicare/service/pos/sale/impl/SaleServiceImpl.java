/**
 * @author : tadiewa
 * date: 9/18/2025
 */


package zw.com.organicare.service.pos.sale.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.com.organicare.constants.MovementType;
import zw.com.organicare.dto.payment.PaymentRequestDto;
import zw.com.organicare.dto.sale.SaleLineRequestDto;
import zw.com.organicare.dto.sale.SaleRequestDto;
import zw.com.organicare.dto.sale.SaleResponseDto;
import zw.com.organicare.exception.InsufficientStockException;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.exception.UserNotFound;
import zw.com.organicare.model.*;
import zw.com.organicare.repository.*;
import zw.com.organicare.service.authService.AuthService;
import zw.com.organicare.service.pos.sale.SaleService;
import zw.com.organicare.utils.SaleMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleLineRepository saleLineRepository;
    private final PaymentRepository paymentRepository;
    private final SalesAgentInventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final StockMovementRepository stockMovementRepository;
    private final UncollectedChangeRepository uncollectedChangeRepository;
    private final AuthService authService;
    @Transactional
    public SaleResponseDto createSale(SaleRequestDto request) {
        // 1. load agent

        User currentUser = authService.getAuthenticatedUser();

        User agent = userRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found"));

       if (Boolean.FALSE.equals(currentUser.getIsActive())){
              throw new UserNotFound("Inactive user cannot perform sales");
       }

        // optional: load patient if provided
        Patient patient = null;
        if (request.getPatientId() != null) {
            patient = patientRepository.findById(request.getPatientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        }
   log.info("branch of current user-------------------------------> {}", currentUser.getBranch());
        // 2. create Sale header and link patient
        Sale sale = Sale.builder()
                .agent(currentUser)
                .patient(patient)
                .saleDate(LocalDateTime.now())
                .totalAmountDue(BigDecimal.ZERO)
                .totalPaid(BigDecimal.ZERO)
                .changeGiven(BigDecimal.ZERO)
                .uncollectedChange(BigDecimal.ZERO)
                .branch(currentUser.getBranch())
               // .saleLines(new ArrayList<>())
               // .payments(new ArrayList<>())
                .build();
        log.info("sale before save:-------------------------------> {}", sale);
        sale = saleRepository.save(sale);

        BigDecimal totalDue = BigDecimal.ZERO;

        // 3. process lines and reduce agent inventory (unchanged)
        for (SaleLineRequestDto lineReq : request.getSaleLines()) {
            Product product = productRepository.findById(lineReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + lineReq.getProductId()));

            SalesAgentInventory inventory = inventoryRepository
                    .findByProduct_ProductIdAndReceivedBy_UserId(product.getProductId(),agent.getUserId())
                    .orElseThrow(() -> new InsufficientStockException("Agent has no stock for product: " + product.getName()));

            if (inventory.getClosingStock() < lineReq.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            // reduce agent inventory
            inventory.setNumberOfProductsSold(inventory.getNumberOfProductsSold() + lineReq.getQuantity());
            inventory.setClosingStock(inventory.getClosingStock() - lineReq.getQuantity());
            inventory.setNumberOfProductsFreelyGiven(inventory.getNumberOfProductsFreelyGiven());
            inventoryRepository.save(inventory);

            // create sale line
            BigDecimal unitPrice =  product.getPrice();
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(lineReq.getQuantity()));

            SaleLine saleLine = SaleLine.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(lineReq.getQuantity())
                    .unitPrice(unitPrice)
                    .lineTotal(lineTotal)
                    .build();
            saleLineRepository.save(saleLine);

            sale.getSaleLines().add(saleLine);
            totalDue = totalDue.add(lineTotal);

            // record stock movement (sale)
            StockMovement movement = StockMovement.builder()
                    .product(product)
                    .quantity(lineReq.getQuantity())
                    .type(MovementType.SALE)
                    .reason("Sold by agent " + agent.getFullName())
                    .movementDate(LocalDateTime.now())
                    .createdBy(agent)
                    .salesAgent(agent)
                    .build();
            stockMovementRepository.save(movement);
        }

        // 4. process payments
        BigDecimal totalPaid = BigDecimal.ZERO;
        for (PaymentRequestDto payReq : request.getPayments()) {
            PaymentType type = paymentTypeRepository.findById(payReq.getPaymentTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("PaymentType not found"));

            Payment payment = Payment.builder()
                    .sale(sale)
                    .paymentType(type)
                    .amount(payReq.getAmount())
                    .paymentDate(LocalDateTime.now())
                    .build();
            paymentRepository.save(payment);

            sale.getPayments().add(payment);
            totalPaid = totalPaid.add(payReq.getAmount());
        }

        sale.setTotalAmountDue(totalDue);
        sale.setTotalPaid(totalPaid);

        // 5. change & uncollected change handling
        BigDecimal diff = totalPaid.subtract(totalDue);
        if (diff.compareTo(BigDecimal.ZERO) < 0) {
            // Not enough money paid
            throw new IllegalArgumentException("Insufficient payment: due " + totalDue + ", paid " + totalPaid);
        }

        if (diff.compareTo(BigDecimal.ZERO) > 0) {
            // change required
            if (request.isChangeProvided()) {
                sale.setChangeGiven(diff);
                sale.setUncollectedChange(BigDecimal.ZERO);
            } else {
                // create UncollectedChange and link patient if present
                UncollectedChange uc = UncollectedChange.builder()
                        .sale(sale)
                        .changeAmount(diff)
                        .createdAt(LocalDateTime.now())
                        .resolved(false)
                        .notes("Change not available at sale time")
                        .salesAgent(agent)
                        .build();

                if (patient != null) {
                    uc.setPatient(patient);
                }

                uncollectedChangeRepository.save(uc);

                sale.setChangeGiven(BigDecimal.ZERO);
                sale.setUncollectedChange(diff);
            }
        } else {
            sale.setChangeGiven(BigDecimal.ZERO);
            sale.setUncollectedChange(BigDecimal.ZERO);
        }

        Sale saved = saleRepository.save(sale);
        return SaleMapper.toDto(saved);
    }

    @Override
    public SaleResponseDto getSale(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + saleId));

        // 2. Map to DTO
        return SaleMapper.toDto(sale);
    }
}

