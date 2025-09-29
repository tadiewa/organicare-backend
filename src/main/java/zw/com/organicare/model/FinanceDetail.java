/**
 * @author : tadiewa
 * date: 9/26/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FinanceDetail")
public class FinanceDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long financeDetailId;
    String  productName;
    Long quantity;
    BigDecimal discount;
    BigDecimal grossRpSales;
    BigDecimal netRpSales;
    BigDecimal rpDf;
    BigDecimal grossCosSales;
    BigDecimal cosConsultationSplit;
    BigDecimal netCosSales;
    BigDecimal totalSales;
    BigDecimal cosDf;
    BigDecimal totalDf;
    BigDecimal amountTendered;
    BigDecimal change;
    @ElementCollection
    @CollectionTable(
            name = "finance_detail_payment_methods",
            joinColumns = @JoinColumn(name = "finance_detail_id")
    )
    @Column(name = "payment_method")
    List<String> paymentMethod = new ArrayList<>();
    String currency;
    String rpName;
    String branchName;
    String salesAgentName;
    String patientName;
    String receiptNumber;
    String npName;
    private LocalDate dateCreated;
}
