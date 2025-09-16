/**
 * @author : tadiewa
 * date: 9/11/2025
 */

package zw.com.organicare.service.patientService;

import zw.com.organicare.dto.patient.PatientDto;

public interface PatientService {

    PatientDto register(PatientDto patientDto);
    PatientDto update(PatientDto patientDto);
}
