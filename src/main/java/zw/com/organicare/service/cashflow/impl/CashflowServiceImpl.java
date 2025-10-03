/**
 * @author : tadiewa
 * date: 10/3/2025
 */


package zw.com.organicare.service.cashflow.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zw.com.organicare.dto.finance.DailyCashflowDto;
import zw.com.organicare.model.*;
import zw.com.organicare.repository.*;
import zw.com.organicare.service.cashflow.CashflowService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CashflowServiceImpl implements CashflowService {

    private final PaymentRepository paymentRepository;
    private final ExpenseRepository expenseRepository;
    private final AccountRepository accountRepository;

    @Override
    public DailyCashflowDto generateDailyCashflow(LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay().minusSeconds(1);

        // 1. Opening balances for all accounts
        Map<String, BigDecimal> openingBalances = new HashMap<>();
        for (Account acc : accountRepository.findAll()) {
            BigDecimal opening = accountRepository.getOpeningBalance(acc.getAccountName(), date)
                    .stream()
                    .findFirst()
                    .orElse(BigDecimal.ZERO);
            openingBalances.put(acc.getAccountName(), opening);
        }

        // 2. Payments aggregated by account
        List<Object[]> paymentTotals = paymentRepository.sumPaymentsByAccountBetweenDates(start, end);
        Map<String, BigDecimal> incomeByAccount = new HashMap<>();
        BigDecimal totalIncome = BigDecimal.ZERO;

        for (Object[] row : paymentTotals) {
            String accountName = (String) row[0];
            BigDecimal amount = (BigDecimal) row[1];
            incomeByAccount.put(accountName, amount);
            totalIncome = totalIncome.add(amount);
        }

        // 3. Expenses from petty cash (PC account)
        BigDecimal totalExpenses = expenseRepository.sumExpensesBetweenDates(date, date);

        // 4. Closing balances per account
        Map<String, BigDecimal> closingBalances = new HashMap<>();
        for (Account acc : accountRepository.findAll()) {
            BigDecimal open = openingBalances.getOrDefault(acc.getAccountName(), BigDecimal.ZERO);
            BigDecimal income = incomeByAccount.getOrDefault(acc.getAccountName(), BigDecimal.ZERO);
            BigDecimal expenses = acc.getAccountName().equals("PC") ? totalExpenses : BigDecimal.ZERO;
            closingBalances.put(acc.getAccountName(), open.add(income).subtract(expenses));
        }

        return DailyCashflowDto.builder()
                .date(date)
                .openingBalanceByAccount(openingBalances)
                .incomeByAccount(incomeByAccount)
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .closingBalanceByAccount(closingBalances)
                .build();
    }
}
