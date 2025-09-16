/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.dto.patientCard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SonographerReportDto {
    private Long reportId;
    private String findings;
    private String impressions;
    private String sonographerNotes;
    private Date reportDate;
    private Long requestFormId;
    private Long performedById;
}
