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
public class BranchRequestDto {
    private String branchName;
    private String branchLocation;
    private String branchCode;
    private boolean isActive;
}
