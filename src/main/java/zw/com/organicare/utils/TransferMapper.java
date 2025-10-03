/**
 * @author : tadiewa
 * date: 10/2/2025
 */


package zw.com.organicare.utils;

import org.springframework.stereotype.Component;
import zw.com.organicare.dto.transfers.TransferResponseDto;
import zw.com.organicare.model.Transfer;

@Component
public class TransferMapper {

    public TransferResponseDto toResponseDto(Transfer transfer) {
        TransferResponseDto dto = new TransferResponseDto();
        dto.setTransId(transfer.getTransId());
        dto.setAmount(transfer.getAmount());
        dto.setTransferredFromAccountName(
                transfer.getTransferredFrom() != null ? transfer.getTransferredFrom().getAccountName() : null);
        dto.setTransferredToAccountName(
                transfer.getTransferredTo() != null ? transfer.getTransferredTo().getAccountName() : null);
        dto.setNote(transfer.getNote());
        dto.setReceivedByName(
                transfer.getReceivedby() != null ? transfer.getReceivedby().getFullName() : null);
        dto.setRequestedByName(
                transfer.getRequestedby() != null ? transfer.getRequestedby().getFullName() : null);
        dto.setAuthorizedByName(
                transfer.getAuthorizedby() != null ? transfer.getAuthorizedby().getFullName() : null);
        dto.setExternalReceiverName(transfer.getExternalReceiverName());
        dto.setExternalReceiverId(transfer.getExternalReceiverId());
        dto.setCreatedOn(transfer.getCreatedOn());
        dto.setAuthorized(transfer.getIsAuthorized());
        dto.setStatus(String.valueOf(transfer.getStatus()));
        return dto;
    }
}

