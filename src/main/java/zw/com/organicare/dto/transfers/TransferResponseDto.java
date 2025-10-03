/**
 * @author : tadiewa
 * date: 10/2/2025
 */


package zw.com.organicare.dto.transfers;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDto {
    private Long transId;
    private BigDecimal amount;
    private String transferredFromAccountName;
    private String transferredToAccountName;
    private String note;
    private String receivedByName;
    private String requestedByName;
    private String authorizedByName;
    private String externalReceiverName;
    private String externalReceiverId;
    private LocalDateTime createdOn;
    private boolean isAuthorized;
    private String status;
}
