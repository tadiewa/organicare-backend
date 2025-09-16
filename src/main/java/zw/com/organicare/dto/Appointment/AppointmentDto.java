/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.dto.Appointment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDto {
    private Long appointmentId;
    private String appointmentNumber;
    private Long patientId;
    private Long doctorId;
    private String branch;
    private Double weight;
    private Double height;
    private String isApproved;
    private String isCompleted;
    private Long createdById;
}

