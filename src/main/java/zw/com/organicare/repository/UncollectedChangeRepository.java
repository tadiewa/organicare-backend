/**
 * @author : tadiewa
 * date: 9/18/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.com.organicare.model.Patient;
import zw.com.organicare.model.UncollectedChange;

import java.util.List;

@Repository
public interface UncollectedChangeRepository  extends JpaRepository<UncollectedChange ,Long> {
    List<UncollectedChange> findByPatientAndResolvedFalse(Patient patient);
    List<UncollectedChange> findByPatient_PatientIdAndResolvedFalse(Long patientId);
}
