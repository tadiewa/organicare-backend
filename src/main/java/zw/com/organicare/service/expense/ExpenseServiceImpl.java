/**
 * @author : tadiewa
 * date: 10/1/2025
 */

package zw.com.organicare.service.expense;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zw.com.organicare.constants.Role;
import zw.com.organicare.dto.expense.ExpenseApprovalRequest;
import zw.com.organicare.dto.expense.ExpenseRequest;
import zw.com.organicare.dto.expense.ExpenseResponseDto;
import zw.com.organicare.exception.ResourceNotFoundException;
import zw.com.organicare.exception.UserNotFound;
import zw.com.organicare.model.Expense;
import zw.com.organicare.model.ExpenseType;
import zw.com.organicare.model.User;
import zw.com.organicare.repository.ExpenseRepository;
import zw.com.organicare.repository.ExpenseTypeRepository;
import zw.com.organicare.repository.UserRepository;
import zw.com.organicare.service.authService.AuthService;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final ExpenseRepository expenseRepository;
    private final ExpenseTypeRepository expenseTypeRepository;


    @Override
    public ExpenseResponseDto createExpense(ExpenseRequest request) {
        log.info("Creating expense:--------------> {}", request);
        User auth = authService.getAuthenticatedUser();
        User receivedBy = userRepository.findById(request.getReceivedBy())
                .orElseThrow(() -> new UserNotFound("ReceivedBy user not found"));

        ExpenseType expenseType = expenseTypeRepository.findById(request.getExpenseType())
                .orElseThrow(() -> new ResourceNotFoundException("Expense type not found with id: " + request.getExpenseType()));

        Expense expense = Expense.builder()
                .amount(request.getAmount())
                .createdAt(LocalDate.now())
                .issuedBy(auth.getFullName())
                .approvedBy("Waiting for approver")
                .receivedBy(receivedBy.getFullName())
                .isApproved(false)
                .branch(auth.getBranch().getBranchName())
                .expenseType(expenseType.getTypeName())
                .type(expenseType)
                .build();

        expenseRepository.save(expense);

        return ExpenseResponseDto.builder()
                .expenseId(expense.getExpenseId())
                .amount(expense.getAmount())
                .createdAt(expense.getCreatedAt())
                .issuedBy(expense.getIssuedBy())
                .approvedBy(expense.getApprovedBy())
                .receivedBy(expense.getReceivedBy())
                .branch(expense.getBranch())
                .expenseType(expense.getExpenseType())
                .build();
    }

    @Override
    public ExpenseResponseDto approve(long expenseId, ExpenseApprovalRequest request) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + expenseId));


        expense.setApproved(request.isApproved());
        expense.setDescription(request.getDescription());
        expense.setApprovedBy(authService.getAuthenticatedUser().getFullName());
        expense.setApprovedAt(LocalDate.now());
        expense = expenseRepository.save(expense);
        return ExpenseResponseDto.builder()
                .expenseId(expense.getExpenseId())
                .amount(expense.getAmount())
                .createdAt(expense.getCreatedAt())
                .issuedBy(expense.getIssuedBy())
                .approvedBy(expense.getApprovedBy())
                .receivedBy(expense.getReceivedBy())
                .isApproved(expense.isApproved())
                .approvedAt(expense.getApprovedAt())
                .description(expense.getDescription())
                .expenseType(expense.getExpenseType())
                .branch(expense.getBranch())
                .build();
    }
}
