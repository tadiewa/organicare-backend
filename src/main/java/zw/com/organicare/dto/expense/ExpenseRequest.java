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


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseRequest {
  private  BigDecimal amount;
  private   Long expenseType;
  private  Long receivedBy;
  private String department;
}
