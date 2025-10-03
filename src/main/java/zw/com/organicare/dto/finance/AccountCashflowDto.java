/**
 * @author : tadiewa
 * date: 10/3/2025
 */


package zw.com.organicare.dto.finance;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCashflowDto {
    private Long accountId;
    private String accountName;
    private BigDecimal openingBalance;
    private BigDecimal income;
    private BigDecimal expenses;
    private BigDecimal transfersIn;
    private BigDecimal transfersOut;
    private BigDecimal closingBalance;
}
