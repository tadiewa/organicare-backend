/**
 * @author : tadiewa
 * date: 10/1/2025
 */

package zw.com.organicare.service.expense;

import zw.com.organicare.dto.expense.ExpenseApprovalRequest;
import zw.com.organicare.dto.expense.ExpenseRequest;
import zw.com.organicare.dto.expense.ExpenseResponseDto;

public interface ExpenseService {
    ExpenseResponseDto createExpense(ExpenseRequest requestDto);

    ExpenseResponseDto approve(long expenseId , ExpenseApprovalRequest requestDto);
}
