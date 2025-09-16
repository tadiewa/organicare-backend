/**
 * @author : tadiewa
 * date: 9/12/2025
 */


package zw.com.organicare.dto.patient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmergencyContactDto {
    @JsonIgnore
    private Long emergencyContactId;
    private String name;
    private String phone;
    private String relationship;
}
