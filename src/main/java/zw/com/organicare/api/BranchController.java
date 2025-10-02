/**
 * @author : tadiewa
 * date: 9/22/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.dto.branch.BranchRequestDto;
import zw.com.organicare.dto.branch.BranchResponseDto;
import zw.com.organicare.dto.branch.BranchStatusUpdateDto;
import zw.com.organicare.service.branch.BranchService;

@RestController
@Slf4j
@RequestMapping("/api/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchResponseDto> createBranch(@RequestBody BranchRequestDto requestDto) {
        BranchResponseDto response = branchService.createBranch(requestDto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{branchId}/status")
    public ResponseEntity<BranchResponseDto> updateBranchStatus(
            @PathVariable Long branchId,
            @RequestBody BranchStatusUpdateDto dto) {
        BranchResponseDto response = branchService.updateBranchStatus(branchId, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{branchId}")
    public  ResponseEntity<BranchResponseDto> getBranch(@PathVariable Long branchId){

        BranchResponseDto response = branchService.getBranch(branchId);
        return ResponseEntity.ok(response);


    }
}
