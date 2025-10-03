/**
 * @author : tadiewa
 * date: 10/1/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long expenseId;
    BigDecimal amount;
    String description;
    LocalDate createdAt;
    LocalDate approvedAt;
    String issuedBy;
    String approvedBy;
    String receivedBy;
    boolean isApproved;
    String expenseType;
    String department;
    String branch;
    @ManyToOne
    @JoinColumn(name = "expense_type_id")
    ExpenseType type;


}
