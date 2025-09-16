/**
 * @author : tadiewa
 * date: 9/11/2025
 */


package zw.com.organicare.dto.referringPrac;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReferringPracDto {
    private Long rp_Id;
    private Double commission_rate;
    private Long userId;
    private Boolean active;



}