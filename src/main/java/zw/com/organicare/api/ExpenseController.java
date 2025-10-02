/**
 * @author : tadiewa
 * date: 10/1/2025
 */


package zw.com.organicare.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.com.organicare.dto.expense.ExpenseApprovalRequest;
import zw.com.organicare.dto.expense.ExpenseRequest;
import zw.com.organicare.dto.expense.ExpenseResponseDto;
import zw.com.organicare.service.expense.ExpenseService;

@RestController
@RequestMapping("/api/expense/")
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    ResponseEntity<ExpenseResponseDto> create(@RequestBody ExpenseRequest request) {
        ExpenseResponseDto response = expenseService.createExpense(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{expenseId}/approve")
    public ResponseEntity<ExpenseResponseDto> approveExpense(
            @PathVariable Long expenseId,
            @RequestBody ExpenseApprovalRequest request
    ) {
        ExpenseResponseDto response = expenseService.approve(expenseId, request);
        return ResponseEntity.ok(response);
    }
}
