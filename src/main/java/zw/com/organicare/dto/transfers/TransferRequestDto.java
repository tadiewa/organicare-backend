/**
 * @author : tadiewa
 * date: 10/2/2025
 */


package zw.com.organicare.dto.transfers;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {
    private BigDecimal amount;
    private Long transferredFromId;
    private Long transferredToId;
    private String note;
    private Long receivedById;
    private String externalReceiverName;
    private String externalReceiverId;
}
