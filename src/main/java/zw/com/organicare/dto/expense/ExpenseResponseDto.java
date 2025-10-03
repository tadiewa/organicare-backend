/**
 * @author : tadiewa
 * date: 10/1/2025
 */


package zw.com.organicare.dto.expense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseResponseDto {
    Long expenseId;
    BigDecimal amount;
    String description;
    LocalDate createdAt;
    LocalDate approvedAt;
    String issuedBy;
    String approvedBy;
    String receivedBy;
    String branch;
    String expenseType;
    String department;
    boolean isApproved;
}
