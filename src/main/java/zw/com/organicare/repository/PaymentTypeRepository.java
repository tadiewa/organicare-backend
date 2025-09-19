/**
 * @author : tadiewa
 * date: 9/18/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.com.organicare.model.PaymentType;

import java.util.Optional;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

    Optional <PaymentType> findByName(String name);
}
