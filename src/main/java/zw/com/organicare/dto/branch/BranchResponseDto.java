/**
 * @author : tadiewa
 * date: 9/22/2025
 */


package zw.com.organicare.dto.branch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponseDto {
    private Long branchId;
    private String branchName;
    private String branchLocation;
    private String branchCode;
    private boolean isActive;
}
