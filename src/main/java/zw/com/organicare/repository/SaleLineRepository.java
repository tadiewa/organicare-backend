/**
 * @author : tadiewa
 * date: 9/18/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.com.organicare.model.SaleLine;

import java.util.List;

public interface SaleLineRepository extends JpaRepository<SaleLine, Long> {
    List<SaleLine> findBySaleId(Long Id);
}
