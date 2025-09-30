/**
 * @author : tadiewa
 * date: 9/11/2025
 */


package zw.com.organicare.dto.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDate;
import java.util.Date;


@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDto {

    private long patientId;
    private String fullName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private String gender;
    private String contactInfo;
    private String address;
    private EmergencyContactDto emergencyContact;
    private String occupation;
    private Long   rp_Id;
}
