/**
 * @author : tadiewa
 * date: 9/22/2025
 */


package zw.com.organicare.dto.branch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchStatusUpdateDto {
    private boolean isActive;
}
