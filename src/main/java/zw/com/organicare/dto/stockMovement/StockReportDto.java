/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.dto.stockMovement;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockReportDto {
    private List<StockSummary> summaries;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StockSummary {
        private String productName;
        private int openingStock;
        private int stockIn;
        private int stockOut;
        private int closingStock;

        private Map<String, Integer> stockInByUser;   // userName -> quantity received
        private Map<String, Integer> stockOutByUser;  // userName -> quantity issued
    }
}

