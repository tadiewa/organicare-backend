/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.dto.patientCard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.com.organicare.dto.Appointment.AppointmentDto;
import zw.com.organicare.dto.RequestForm.RequestFormDto;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientCardDto {
    private Long patientCardId;
    private String cardNumber;

    private String patientHistory;
    private String familyHistory;
    private String socialHistory;
    private String presentingComplaints;
    private String diagnosis;
    private String remarks;
    private String treatmentPlan;
    private String duration;
    private Date reviewDate;
    private String patientSignature;
    private String doctorSignature;
    private Boolean ultrasoundRequestFlag;

    private Long patientId;

    private List<RequestFormDto> requestForms;

}

