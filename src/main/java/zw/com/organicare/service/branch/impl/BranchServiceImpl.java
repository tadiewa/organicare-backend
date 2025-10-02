/**
 * @author : tadiewa
 * date: 9/22/2025
 */


package zw.com.organicare.service.branch.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.com.organicare.dto.branch.BranchRequestDto;
import zw.com.organicare.dto.branch.BranchResponseDto;
import zw.com.organicare.dto.branch.BranchStatusUpdateDto;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.model.Branch;
import zw.com.organicare.repository.BranchRepository;
import zw.com.organicare.service.branch.BranchService;
import zw.com.organicare.utils.BranchMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    @Transactional
    public BranchResponseDto createBranch(BranchRequestDto requestDto) {
        Branch branch = BranchMapper.toEntity(requestDto);
        Branch saved = branchRepository.save(branch);
        return BranchMapper.toDto(saved);
    }


    @Override
    @Transactional
    public BranchResponseDto updateBranchStatus(Long branchId, BranchStatusUpdateDto dto) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId));

        branch.setActive(dto.isActive());
        Branch updated = branchRepository.save(branch);

        return BranchMapper.toDto(updated);
    }

    @Override
    public BranchResponseDto getBranch(Long branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId));

        if(!branch.isActive())
            throw new ResourceNotFoundException("branch not configured");

        return BranchMapper.toDto(branch);
    }

}
