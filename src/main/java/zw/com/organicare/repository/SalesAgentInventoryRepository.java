/**
 * @author : tadiewa
 * date: 9/16/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.com.organicare.model.Product;
import zw.com.organicare.model.SalesAgentInventory;
import zw.com.organicare.model.User;

import java.util.List;
import java.util.Optional;

public interface SalesAgentInventoryRepository extends JpaRepository<SalesAgentInventory, Long> {
    Page<SalesAgentInventory> findByReceivedBy_UserId(Long userId, Pageable pageable);
    List<SalesAgentInventory> findByProduct_ProductId(Long productId);
    Optional<SalesAgentInventory> findByReceivedBy_UserIdAndProduct_ProductId(Long userId, Long productId);
    Optional<SalesAgentInventory>  findByReceivedByAndProduct(User agent, Product product);
    //Optional<SalesAgentInventory> findByProductIdAndReceivedBy_UserId(Long productId, Long agentId);
    Optional<SalesAgentInventory> findByProduct_ProductIdAndReceivedBy_UserId(Long productId, Long userId);

    List<SalesAgentInventory> findAllByReceivedBy_UserId(Long agentId);

}
