/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.service.stockMovement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zw.com.organicare.constants.MovementType;
import zw.com.organicare.dto.stockMovement.StockMovementRequestDto;
import zw.com.organicare.dto.stockMovement.StockMovementResponseDto;
import zw.com.organicare.dto.stockMovement.StockReportDto;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.model.Product;
import zw.com.organicare.model.StockMovement;
import zw.com.organicare.model.User;
import zw.com.organicare.repository.ProductRepository;
import zw.com.organicare.repository.StockMovementRepository;
import zw.com.organicare.repository.UserRepository;
import zw.com.organicare.utils.StockMovementMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository movementRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public StockMovementResponseDto recordMovement(StockMovementRequestDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        User user = userRepository.findById(dto.getCreatedById())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        StockMovement movement = StockMovementMapper.toEntity(dto, product, user);
        StockMovement saved = movementRepository.save(movement);

        return StockMovementMapper.toDto(saved);
    }

    @Override
    public List<StockMovementResponseDto> getMovementsForProduct(Long productId, LocalDate start, LocalDate end) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        List<StockMovement> movements = movementRepository.findByProductAndMovementDateBetween(
                product, start.atStartOfDay(), end.atTime(LocalTime.MAX)
        );

        return movements.stream().map(StockMovementMapper::toDto).toList();
    }

    @Override
    public StockReportDto generateReport(LocalDate start, LocalDate end) {
        List<StockMovement> movements = movementRepository.findByMovementDateBetween(
                start.atStartOfDay(), end.atTime(LocalTime.MAX)
        );

        Map<Long, StockReportDto.StockSummary> summaryMap = new HashMap<>();

        for (StockMovement m : movements) {
            summaryMap.computeIfAbsent(m.getProduct().getProductId(), id ->
                    new StockReportDto.StockSummary(
                            m.getProduct().getName(),
                            0, 0, 0, 0,
                            new HashMap<>(),
                            new HashMap<>()
                    ));

            StockReportDto.StockSummary summary = summaryMap.get(m.getProduct().getProductId());

            if (m.getType() == MovementType.IN || m.getType() == MovementType.RETURN) {
                summary.setStockIn(summary.getStockIn() + m.getQuantity());
                summary.getStockInByUser().merge(m.getCreatedBy().getFullName(), m.getQuantity(), Integer::sum);
            } else {
                summary.setStockOut(summary.getStockOut() + m.getQuantity());
                summary.getStockOutByUser().merge(m.getCreatedBy().getFullName(), m.getQuantity(), Integer::sum);
            }

            summary.setClosingStock(summary.getOpeningStock() + summary.getStockIn() - summary.getStockOut());
        }

        return new StockReportDto(new ArrayList<>(summaryMap.values()));
    }
}

