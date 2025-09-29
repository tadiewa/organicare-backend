/**
 * @author : tadiewa
 * date: 9/26/2025
 */


package zw.com.organicare.dto.sale;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RpSaleDto {

    private String rpName;
    private Long saleId;
    private BigDecimal totalAmountDue;
    private BigDecimal totalPaid;
    private BigDecimal changeGiven;
    private BigDecimal uncollectedChange;
    private LocalDateTime saleDate;
    private List<String> products; // List of product names
    private String branchName;
    private String agentName;
    private String patientName;
}
