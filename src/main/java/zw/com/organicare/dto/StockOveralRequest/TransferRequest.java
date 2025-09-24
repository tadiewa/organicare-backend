/**
 * @author : tadiewa
 * date: 9/24/2025
 */


package zw.com.organicare.dto.StockOveralRequest;

import lombok.*;

import zw.com.organicare.constants.MovementType;


import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {

    List<VarienceItemDto> items;
    Long agentId;
    String reason;
}
