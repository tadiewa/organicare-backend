/**
 * @author : tadiewa
 * date: 9/18/2025
 */


package zw.com.organicare.service.pos.sale.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.com.organicare.api.PatientController;
import zw.com.organicare.constants.MovementType;
import zw.com.organicare.dto.finance.FinanceDetailResponseDto;
import zw.com.organicare.dto.payment.PaymentRequestDto;
import zw.com.organicare.dto.sale.*;
import zw.com.organicare.exception.InsufficientStockException;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.exception.UserNotFound;
import zw.com.organicare.model.*;
import zw.com.organicare.repository.*;
import zw.com.organicare.service.authService.AuthService;
import zw.com.organicare.service.pos.sale.SaleService;
import zw.com.organicare.utils.CodeGeneratorService;
import zw.com.organicare.utils.ProductCodeGenerator;
import zw.com.organicare.utils.SaleMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private final CodeGeneratorService codeGenerator;
    private final FinanceDetailRepository financeDetailRepository;
    private final BranchRepository branchRepository;
    private final AccountRepository accountRepository;


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

        User np = null;
        if (request.getNpId() != null) {
            np = userRepository.findById(request.getNpId())
                    .orElseThrow(() -> new ResourceNotFoundException("Nurse Practitioner not found"));
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
                .subTotal(BigDecimal.ZERO)
               // .saleLines(new ArrayList<>())
               // .payments(new ArrayList<>())
                .build();
        log.info("sale before save:-------------------------------> {}", sale);
        sale = saleRepository.save(sale);

        BigDecimal totalDue = BigDecimal.ZERO;
        BigDecimal lineTotal = BigDecimal.ZERO;

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
           BigDecimal saleLineTotal = unitPrice.multiply(BigDecimal.valueOf(lineReq.getQuantity()));

           //fixed sale line total calculation
            lineTotal = lineTotal.add(saleLineTotal);
            SaleLine saleLine = SaleLine.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(lineReq.getQuantity())
                    .unitPrice(unitPrice)
                    .lineTotal(saleLineTotal)
                    .createdAt(LocalDateTime.now())
                    .build();
            saleLineRepository.save(saleLine);

            sale.getSaleLines().add(saleLine);

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
        totalDue = totalDue.add(lineTotal.subtract(request.getDiscount() != null ? BigDecimal.valueOf(request.getDiscount()) : BigDecimal.ZERO));
        sale.setSubTotal(lineTotal);
        // 4. process payments
        BigDecimal totalPaid = BigDecimal.ZERO;
        for (PaymentRequestDto payReq : request.getPayments()) {
            PaymentType type = paymentTypeRepository.findById(payReq.getPaymentTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("PaymentType not found"));

            Account  account = accountRepository.findAccountByAccountName(type.getName())
                            .orElseThrow(()-> new ResourceNotFoundException("No account for the payment type:" + " "+ type.getName()));

            Payment payment = Payment.builder()
                    .sale(sale)
                    .paymentType(type)
                    .amount(payReq.getAmount())
                    .paymentDate(LocalDateTime.now())
                    .account(account)
                    .build();
            paymentRepository.save(payment);

            sale.getPayments().add(payment);
            totalPaid = totalPaid.add(payReq.getAmount());
        }
       // sale.setSubTotal(lineTotal);
        sale.setTotalAmountDue(totalDue);
        sale.setNp(request.getNpId() != null ? np : null);
        sale.setTotalPaid(totalPaid);
        sale.setDiscount(request.getDiscount() != null ? BigDecimal.valueOf(request.getDiscount()) : BigDecimal.ZERO);
        String receiptNumber = generateUniqueSaleCode();
        sale.setReceiptNumber(receiptNumber);

        // 5. change & uncollected change handling
        BigDecimal diff = totalPaid.subtract(totalDue);

//         if (diff.compareTo(BigDecimal.ZERO) < 0) {
//            // Not enough money paid
//            throw new IllegalArgumentException("Insufficient payment: due " + totalDue + ", paid " + totalPaid);
//         }

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
                        .receiptNumber(receiptNumber)
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
        financialSplit(sale);
        return SaleMapper.toDto(saved);
    }


    FinanceDetailResponseDto financialSplit(Sale sale){
        FinanceDetail financeBreak = new FinanceDetail();

        Patient patient = patientRepository.findById(sale.getPatient().getPatientId())
                .orElseThrow(() -> new UserNotFound("Patient not found with id: " + sale.getPatient().getPatientId()));
       List <Payment> payment =  paymentRepository.findBySaleId(sale.getId());
       if(payment.isEmpty()) {
           throw new ResourceNotFoundException("Payment not found for sale id: " + sale.getId());
       }

        List<String> paymentTypes = payment.stream()
                .map(p -> p.getPaymentType().getName())
                .distinct()
                .toList();

        log.info("Payment types:--------------------------------> {}", paymentTypes);

        Branch branch = branchRepository.findById(sale.getBranch().getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + sale.getBranch().getBranchId()));
        User saleAgent = userRepository.findById(sale.getAgent().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Sales agent not found with id: " + sale.getAgent().getUserId()));
        User np = userRepository.findById(sale.getAgent().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Sales agent not found with id: " + sale.getAgent().getUserId()));

        String rpName = patientRepository.findRpNameByPatientId(sale.getPatient().getPatientId());
        financeBreak.setReceiptNumber(sale.getReceiptNumber());
        financeBreak.setChange(sale.getUncollectedChange());
        //amount given by the customer before change
        BigDecimal  grossCosSales = BigDecimal.ZERO;
        financeBreak.setAmountTendered(sale.getTotalPaid());
        BigDecimal cosDf = BigDecimal.ZERO;
        BigDecimal netRpSales = BigDecimal.ZERO;
        BigDecimal rpDf = BigDecimal.ZERO;
        BigDecimal grossRpSales = BigDecimal.ZERO;
        BigDecimal discount = sale.getDiscount();
        financeBreak.setDiscount(discount);
        BigDecimal cosConsSplit =  BigDecimal.valueOf(15);
        // total amount for the products sale
        financeBreak.setTotalSales(sale.getTotalAmountDue());
        financeBreak.setPatientName(patient.getFullName());
        financeBreak.setBranchName(branch.getBranchName());
        financeBreak.setSalesAgentName(saleAgent.getFullName());
        financeBreak.setDateCreated(sale.getSaleDate().toLocalDate());
        financeBreak.setRpName(rpName);
        financeBreak.setDiscount(BigDecimal.ZERO);
        financeBreak.setPaymentMethod(paymentTypes);
        financeBreak.setNpName(np.getFullName());

        for(SaleLine lineReq :sale.getSaleLines()){
            Product product = productRepository.findById(lineReq.getProduct().getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + lineReq.getProduct().getName()));

            financeBreak.setProductName(product.getName());
            financeBreak.setQuantity(Long.valueOf(lineReq.getQuantity()));
            if(product.isActive()&& product.isCos()){
                if(product.getName().equals("Review")|| product.getName().equals("Ultrasound")) {
                    grossCosSales = grossCosSales.add(lineReq.getUnitPrice().multiply(BigDecimal.valueOf(lineReq.getQuantity())));
                    cosDf = cosDf.add(BigDecimal.ZERO.multiply(BigDecimal.valueOf(lineReq.getQuantity())));
                }else {

                    grossCosSales = grossCosSales.add(lineReq.getUnitPrice().multiply(BigDecimal.valueOf(lineReq.getQuantity())));
                    cosDf = cosDf.add(BigDecimal.TWO.multiply(BigDecimal.valueOf(lineReq.getQuantity())));
                }

            }else
                if(product.isActive()&& product.getName().equals("Consultation")){

                BigDecimal consPrice = lineReq.getUnitPrice().multiply(BigDecimal.valueOf(lineReq.getQuantity()));
                grossRpSales = grossRpSales.add(consPrice);
                netRpSales  = netRpSales.add (grossRpSales.subtract(cosConsSplit).subtract(discount));
                financeBreak.setNetRpSales(netRpSales);
                financeBreak.setCosConsultationSplit(cosConsSplit);

            } else {

                grossRpSales = grossRpSales.add(lineReq.getUnitPrice().multiply(BigDecimal.valueOf(lineReq.getQuantity())));
                rpDf = rpDf.add(BigDecimal.TWO.multiply(BigDecimal.valueOf(lineReq.getQuantity())));
                financeBreak.setNetRpSales(grossRpSales.subtract(rpDf).subtract(discount));
            }
        }
        financeBreak.setGrossCosSales(grossCosSales);
        financeBreak.setCosDf(cosDf);
        financeBreak.setNetCosSales(grossCosSales.subtract(cosDf));
        financeBreak.setRpDf(rpDf);
        financeBreak.setGrossRpSales(grossRpSales);
        financeBreak.setTotalDf(rpDf.add(cosDf));
        financeDetailRepository.save(financeBreak);

        return FinanceDetailResponseDto.builder()
                .receiptNumber(financeBreak.getReceiptNumber())
                .change(financeBreak.getChange())
                .amountTendered(financeBreak.getAmountTendered())
                .totalSales(financeBreak.getTotalSales())
                .patientName(financeBreak.getPatientName())
                .branchName(financeBreak.getBranchName())
                .salesAgentName(financeBreak.getSalesAgentName())
                .dateCreated(financeBreak.getDateCreated())
                .rpName(financeBreak.getRpName())
                .discount(financeBreak.getDiscount())
                .paymentMethod(financeBreak.getPaymentMethod())
                .npName(financeBreak.getNpName())
                .grossRpSales(financeBreak.getGrossRpSales())
                .grossCosSales(financeBreak.getGrossCosSales())
                .netRpSales(financeBreak.getNetRpSales())
                .netCosSales(financeBreak.getNetCosSales())
                .quantity(financeBreak.getQuantity())
                .productName(financeBreak.getProductName())
                .rpDf(financeBreak.getRpDf())
                .cosDf(financeBreak.getCosDf())
                .totalDf(financeBreak.getTotalDf())
                .build();
    }




    @Override
    public SaleResponseDto getSale(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + saleId));

        // 2. Map to DTO
        return SaleMapper.toDto(sale);
    }

    public List<DailyAgentSalesDto> getAgentTotals(LocalDate date) {
        return saleRepository.getDailyTotalsByAgent(date);
    }

    public List<DailyRpSalesDto> getRpTotals(LocalDate date) {
        return saleRepository.getDailyTotalsByRp(date);
    }



    public List<RpSaleDto> getSalesByRpAndDateRange(String rpName, LocalDateTime start, LocalDateTime end) {
        List<Sale> sales = saleRepository.findSalesWithProductsByRpAndDateRange(rpName, start, end);

        return sales.stream().map(sale -> {
            List<String> productNames = sale.getSaleLines().stream()
                    .map(sl -> sl.getProduct().getName())
                    .toList();

        String referringPracName = patientRepository.findRpNameByPatientId(sale.getPatient().getPatientId());
            return RpSaleDto.builder()
                    .rpName(referringPracName)
                    .saleId(sale.getId())
                    .totalAmountDue(sale.getTotalAmountDue())
                    .totalPaid(sale.getTotalPaid())
                   // .changeGiven(sale.getChangeGiven())
                    .uncollectedChange(sale.getUncollectedChange())
                    .saleDate(sale.getSaleDate())
                    .products(productNames)
                    .branchName(sale.getBranch().getBranchName())
                    .agentName(sale.getAgent().getFullName())
                    .patientName(sale.getPatient().getFullName())
                    .build();
        }).toList();
    }


    private String generateUniqueSaleCode() {
        String code;
        do {
            code = codeGenerator.generateSaleReceipt();
        } while (productRepository.existsByProductCode(code));
        return code;
    }
}

