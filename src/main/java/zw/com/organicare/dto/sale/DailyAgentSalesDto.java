/**
 * @author : tadiewa
 * date: 9/26/2025
 */


package zw.com.organicare.dto.sale;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyAgentSalesDto {
    private Long agentId;
    private String agentName;
    private BigDecimal totalAmount;
}
