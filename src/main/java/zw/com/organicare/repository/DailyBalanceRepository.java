/**
 * @author : tadiewa
 * date: 10/3/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.com.organicare.model.DailyBalance;

import java.time.LocalDate;
import java.util.List;

public interface DailyBalanceRepository extends JpaRepository<DailyBalance, Long> {
    List<DailyBalance> findByDate(LocalDate date);
}
