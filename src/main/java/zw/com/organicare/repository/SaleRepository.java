/**
 * @author : tadiewa
 * date: 9/18/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.com.organicare.model.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
