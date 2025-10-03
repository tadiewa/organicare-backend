/**
 * @author : tadiewa
 * date: 10/2/2025
 */


package zw.com.organicare.service.transfer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zw.com.organicare.constants.TransferStatus;
import zw.com.organicare.dto.transfers.TransferApprovalRequest;
import zw.com.organicare.dto.transfers.TransferRequestDto;
import zw.com.organicare.dto.transfers.TransferResponseDto;
import zw.com.organicare.exception.AlreadyExistsException;
import zw.com.organicare.exception.ExceedsAmountException;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.model.Account;
import zw.com.organicare.model.Transfer;
import zw.com.organicare.model.User;
import zw.com.organicare.repository.AccountRepository;
import zw.com.organicare.repository.TransferRepository;
import zw.com.organicare.repository.UserRepository;
import zw.com.organicare.service.authService.AuthService;
import zw.com.organicare.utils.TransferMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransferMapper transferMapper;
    private final AuthService authService;

    @Override
    public TransferResponseDto createTransfer(TransferRequestDto request) {
        Transfer transfer = new Transfer();

        User requester = authService.getAuthenticatedUser();

        Account fromAccount =  accountRepository.findById(request.getTransferredFromId()).
                orElseThrow(() -> new ResourceNotFoundException("Account not found"));


        Account toAccount = accountRepository.findById(request.getTransferredToId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        BigDecimal amount =  request.getAmount();

        transfer.setTransferredFrom(fromAccount);
        transfer.setTransferredTo(toAccount);
        transfer.setRequestedby(requester);
        transfer.setAmount(amount);
        transfer.setStatus(TransferStatus.valueOf(String.valueOf(TransferStatus.PENDING)));
        transfer.setIsAuthorized(false);
        transfer.setNote(request.getNote());
        transfer.setReceivedby(userRepository.findById(request.getReceivedById())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        transfer.setExternalReceiverName(request.getExternalReceiverName());
        transfer.setExternalReceiverId(request.getExternalReceiverId());
        transfer.setCreatedOn(LocalDateTime.now());

        Transfer saved = transferRepository.save(transfer);
        return transferMapper.toResponseDto(saved);
    }

    @Override
    public TransferResponseDto getTransferById(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transfer not found"));
        return transferMapper.toResponseDto(transfer);
    }

    @Override
    public List<TransferResponseDto> getAllTransfers() {
        return transferRepository.findAll()
                .stream()
                .map(transferMapper::toResponseDto)
                .toList();
    }

    @Override
    public TransferResponseDto approveTransfer(Long transferId,  TransferApprovalRequest approvalRequest) {
        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new ResourceNotFoundException("Transfer not found"));
        BigDecimal reducedAmount= BigDecimal.ZERO;
        BigDecimal increaseAmount= BigDecimal.ZERO;
        Account fromAccount = accountRepository.findAccountByAccountName(transfer.getTransferredFrom().getAccountName());
        Account toAccount = accountRepository.findAccountByAccountName(transfer.getTransferredTo().getAccountName());

        if(transfer.getAmount().compareTo(fromAccount.getAccountBalance()) > 0) {

            throw new ExceedsAmountException("Insufficient Funds. Current balance: " + fromAccount.getAccountBalance());
        }

        if (transfer.getStatus() != TransferStatus.PENDING) {
            throw new AlreadyExistsException("Transfer already " + transfer.getStatus());
        }

        User authorizer = authService.getAuthenticatedUser();

        transfer.setAuthorizedby(authorizer);
        transfer.setNote(approvalRequest.getNote());
        transfer.setIsAuthorized(approvalRequest.getIsAuthorized());

        if (approvalRequest.getIsAuthorized().equals(true)) {
            transfer.setStatus(TransferStatus.AUTHORIZED);

            reducedAmount= reducedAmount.add(fromAccount.getAccountBalance().subtract(transfer.getAmount()));
            fromAccount.setAccountBalance(reducedAmount);
            fromAccount.setClosingDate(LocalDate.now());

            increaseAmount =increaseAmount.add(toAccount.getAccountBalance().add(transfer.getAmount()));
            toAccount.setAccountBalance(increaseAmount);
            toAccount.setClosingDate(LocalDate.now());

        } else {
            transfer.setStatus(TransferStatus.REJECTED);
        }
        transfer.setAuthRejDate(LocalDateTime.now());

        accountRepository.saveAll(Arrays.asList(toAccount,fromAccount));

        Transfer saved = transferRepository.save(transfer);
        return transferMapper.toResponseDto(saved);
    }

}

