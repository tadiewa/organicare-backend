/**
 * @author : tadiewa
 * date: 10/1/2025
 */


package zw.com.organicare.dto.expense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseApprovalRequest {
    private boolean approved;
    private String description;
}
