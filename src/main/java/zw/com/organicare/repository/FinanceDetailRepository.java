/**
 * @author : tadiewa
 * date: 9/29/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.com.organicare.model.FinanceDetail;

@Repository
public interface FinanceDetailRepository extends JpaRepository<FinanceDetail, Long> {
}
