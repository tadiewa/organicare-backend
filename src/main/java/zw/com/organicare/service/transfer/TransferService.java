/**
 * @author : tadiewa
 * date: 10/2/2025
 */


package zw.com.organicare.service.transfer;

import zw.com.organicare.dto.transfers.TransferApprovalRequest;
import zw.com.organicare.dto.transfers.TransferRequestDto;
import zw.com.organicare.dto.transfers.TransferResponseDto;

import java.util.List;

public interface TransferService {
    TransferResponseDto createTransfer(TransferRequestDto request);
    TransferResponseDto getTransferById(Long id);
    List<TransferResponseDto> getAllTransfers();
    TransferResponseDto approveTransfer(Long transferId,  TransferApprovalRequest approvalRequest);
}
