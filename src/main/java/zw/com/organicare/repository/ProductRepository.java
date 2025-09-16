/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.com.organicare.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductCode(String productCode);
}
