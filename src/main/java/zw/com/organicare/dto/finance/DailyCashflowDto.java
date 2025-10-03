/**
 * @author : tadiewa
 * date: 10/3/2025
 */


package zw.com.organicare.dto.finance;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyCashflowDto {
    private LocalDate date;
    private Map<String, BigDecimal> openingBalanceByAccount;
    private Map<String, BigDecimal> incomeByAccount;
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private Map<String, BigDecimal> closingBalanceByAccount;
}
