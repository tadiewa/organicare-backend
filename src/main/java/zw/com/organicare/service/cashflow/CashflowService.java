/**
 * @author : tadiewa
 * date: 10/3/2025
 */

package zw.com.organicare.service.cashflow;

import zw.com.organicare.dto.finance.DailyCashflowDto;

import java.time.LocalDate;

public interface CashflowService {
    DailyCashflowDto generateDailyCashflow(LocalDate date);
}
