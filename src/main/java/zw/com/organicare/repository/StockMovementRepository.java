/**
 * @author : tadiewa
 * date: 9/17/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.com.organicare.model.Product;
import zw.com.organicare.model.StockMovement;

import java.time.LocalDateTime;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProductAndMovementDateBetween(
            Product product, LocalDateTime start, LocalDateTime end
    );

    List<StockMovement> findByMovementDateBetween(LocalDateTime start, LocalDateTime end);
}
