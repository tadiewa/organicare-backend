/**
 * @author : tadiewa
 * date: 9/11/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import zw.com.organicare.model.ReferringPractitioner;

import java.util.Optional;

@Repository
public interface ReferringPractitionerRespository extends JpaRepository <ReferringPractitioner, Long> , JpaSpecificationExecutor<ReferringPractitioner> {

   Optional<ReferringPractitioner> findById(Long id);
}
