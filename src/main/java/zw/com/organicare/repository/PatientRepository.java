/**
 * @author : tadiewa
 * date: 9/11/2025
 */

package zw.com.organicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zw.com.organicare.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {
    @Query("""
        SELECT rp.user.fullName 
        FROM Patient p
        JOIN p.referringPractitioner rp
        JOIN rp.user u
        WHERE p.patientId = :patientId
    """)
    String findRpNameByPatientId(@Param("patientId") Long patientId);
}
