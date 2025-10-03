/**
 * @author : tadiewa
 * date: 9/18/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.com.organicare.model.Payment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findBySaleId(Long id);


    @Query("""
        SELECT p.account.accountName, SUM(p.amount)
        FROM Payment p
        WHERE p.paymentDate BETWEEN :start AND :end
        GROUP BY p.account.accountName
    """)
    List<Object[]> sumPaymentsByAccountBetweenDates(@Param("start") LocalDateTime start,
                                                    @Param("end") LocalDateTime end);
}
