/**
 * @author : tadiewa
 * date: 10/2/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.dto.transfers.TransferApprovalRequest;
import zw.com.organicare.dto.transfers.TransferRequestDto;
import zw.com.organicare.dto.transfers.TransferResponseDto;
import zw.com.organicare.service.transfer.TransferService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class FinanceTransferController {
    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferResponseDto> createTransfer(@RequestBody TransferRequestDto request) {
        TransferResponseDto response = transferService.createTransfer(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferResponseDto> getTransferById(@PathVariable Long id) {
        return ResponseEntity.ok(transferService.getTransferById(id));
    }

    @GetMapping
    public ResponseEntity<List<TransferResponseDto>> getAllTransfers() {
        return ResponseEntity.ok(transferService.getAllTransfers());
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<TransferResponseDto> approveTransfer(
            @PathVariable Long id,
            @RequestBody TransferApprovalRequest approvalRequest) {
        return ResponseEntity.ok(transferService.approveTransfer(id,  approvalRequest));
    }

}
