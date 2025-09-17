/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.service.StockOveralService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zw.com.organicare.dto.StockOveralRequest.StockOveralRequestDto;
import zw.com.organicare.dto.StockOveralRequest.StockOveralResponseDto;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.model.Product;
import zw.com.organicare.model.StockOveral;
import zw.com.organicare.model.User;
import zw.com.organicare.repository.ProductRepository;
import zw.com.organicare.repository.StockOveralRepository;
import zw.com.organicare.repository.UserRepository;
import zw.com.organicare.utils.StockOveralMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockOveralServiceImpl implements StockOveralService {

    private final StockOveralRepository stockOveralRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

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
}


