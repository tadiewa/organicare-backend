/**
 * @author : tadiewa
 * date: 9/22/2025
 */


package zw.com.organicare.service.branch;

import zw.com.organicare.dto.branch.BranchRequestDto;
import zw.com.organicare.dto.branch.BranchResponseDto;
import zw.com.organicare.dto.branch.BranchStatusUpdateDto;

public interface BranchService {
    BranchResponseDto createBranch(BranchRequestDto requestDto);
    BranchResponseDto updateBranchStatus(Long branchId, BranchStatusUpdateDto dto);

}
