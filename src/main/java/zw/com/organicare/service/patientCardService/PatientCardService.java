/**
 * @author : tadiewa
 * date: 9/15/2025
 */

package zw.com.organicare.service.patientCardService;

import jakarta.servlet.http.HttpServletRequest;
import zw.com.organicare.dto.RequestForm.RequestFormDto;
import zw.com.organicare.dto.patientCard.PatientCardDto;
import zw.com.organicare.dto.patientCard.SonographerReportDto;

public interface PatientCardService {
    PatientCardDto createPatientCard(Long patientId, PatientCardDto dto);

    PatientCardDto getPatientCard(Long cardId);

    PatientCardDto updatePatientCard(Long cardId, PatientCardDto dto);

    RequestFormDto addRequestForm(Long cardId, RequestFormDto dto);

    SonographerReportDto addSonographerReport(Long requestFormId, SonographerReportDto dto);
}
