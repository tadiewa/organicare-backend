/**
 * @author : tadiewa
 * date: 10/1/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zw.com.organicare.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
            "WHERE e.createdAt BETWEEN :start AND :end AND e.isApproved = true")
    Optional<BigDecimal> findTotalByDate(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end);


    @Query("""
        SELECT COALESCE(SUM(e.amount), 0)
        FROM Expense e
        WHERE e.approvedAt BETWEEN :start AND :end
          AND e.isApproved = true
    """)
    BigDecimal sumExpensesBetweenDates(@Param("start") LocalDate start,
                                       @Param("end") LocalDate end);


}
