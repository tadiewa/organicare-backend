/**
 * @author : tadiewa
 * date: 9/16/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.com.organicare.model.SalesAgentInventory;

import java.util.List;
import java.util.Optional;

public interface SalesAgentInventoryRepository extends JpaRepository<SalesAgentInventory, Long> {
    List<SalesAgentInventory> findByReceivedBy_UserId(Long userId);
    List<SalesAgentInventory> findByProduct_ProductId(Long productId);

   Optional<SalesAgentInventory> findByReceivedBy_UserIdAndProduct_ProductId(Long userId, Long productId);
}
