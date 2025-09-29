/**
 * @author : tadiewa
 * date: 9/26/2025
 */


package zw.com.organicare.dto.finance;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanceDetailResponseDto {
    private BigDecimal discount;
    private BigDecimal grossRpSales;
    private BigDecimal netRpSales;
    private Long quantity;
    private BigDecimal rpDf;
    private BigDecimal grossCosSales;
    private BigDecimal cosConsultationSplit;
    private BigDecimal netCosSales;
    private BigDecimal totalSales;
    private BigDecimal cosDf;
    private BigDecimal totalDf;
    private BigDecimal amountTendered;
    private BigDecimal change;
    private List<String> paymentMethod;
    private String productName;
    private String currency;
    private String rpName;
    private String branchName;
    private String salesAgentName;
    private String patientName;
    private String receiptNumber;
    private String npName;
    private LocalDate dateCreated;
}
