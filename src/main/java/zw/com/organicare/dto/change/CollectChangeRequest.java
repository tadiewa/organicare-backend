/**
 * @author : tadiewa
 * date: 9/19/2025
 */


package zw.com.organicare.dto.change;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectChangeRequest {
    private Long uncollectedChangeId;
    private Long patientId;
}
