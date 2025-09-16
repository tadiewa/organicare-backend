/**
 * @author : tadiewa
 * date: 9/15/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.com.organicare.model.PatientCard;

import java.util.Optional;

@Repository
public interface PatientCardRepository extends JpaRepository<PatientCard, Long> {
    Optional<PatientCard> findTopByOrderByPatientCardIdDesc();
}
