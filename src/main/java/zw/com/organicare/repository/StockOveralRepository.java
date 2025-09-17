/**
 * @author : tadiewa
 * date: 9/16/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.com.organicare.model.StockOveral;

import java.util.List;
import java.util.Optional;

public interface StockOveralRepository extends JpaRepository<StockOveral, Long> {

  Optional<StockOveral>findByProduct_ProductId(Long productId);
}
