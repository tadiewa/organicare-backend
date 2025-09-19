/**
 * @author : tadiewa
 * date: 9/19/2025
 */


package zw.com.organicare.dto.change;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UncollectedChangeResponse {
    private Long id;
    private BigDecimal changeAmount;
    private boolean resolved;
    private LocalDateTime createdAt;
    private String notes;
    private String patientName;
    private String salesAgentName;
}
