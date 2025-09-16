/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.dto.RequestForm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestFormDto {
    private Long requestFormId;
    private String medicalAid;
    private String lmp;
    private String clinicalHistory;
    private String referringDoctor;
    private Date date;
    private List<String> ultraSoundRequestedOn;
}