/**
 * @author : tadiewa
 * date: 9/15/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.com.organicare.model.Appointment;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctor_UserId(Long doctorId);
    List<Appointment> findAllByPatient_PatientId(Long patientId);
}
