/**
 * @author : tadiewa
 * date: 9/18/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.com.organicare.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findBySaleId(Long id);
}
