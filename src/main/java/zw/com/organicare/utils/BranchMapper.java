/**
 * @author : tadiewa
 * date: 9/22/2025
 */


package zw.com.organicare.utils;

import zw.com.organicare.dto.branch.BranchRequestDto;
import zw.com.organicare.dto.branch.BranchResponseDto;
import zw.com.organicare.model.Branch;

public class BranchMapper {
    public static Branch toEntity(BranchRequestDto dto) {
        return Branch.builder()
                .branchName(dto.getBranchName())
                .branchLocation(dto.getBranchLocation())
                .branchCode(dto.getBranchCode())
                .isActive(dto.isActive())
                .build();
    }

    public static BranchResponseDto toDto(Branch entity) {
        BranchResponseDto dto = new BranchResponseDto();
        dto.setBranchId(entity.getBranchId());
        dto.setBranchName(entity.getBranchName());
        dto.setBranchLocation(entity.getBranchLocation());
        dto.setBranchCode(entity.getBranchCode());
        dto.setActive(entity.isActive());
        return dto;
    }
}