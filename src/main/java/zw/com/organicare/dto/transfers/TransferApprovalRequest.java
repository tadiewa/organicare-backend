/**
 * @author : tadiewa
 * date: 10/2/2025
 */


package zw.com.organicare.dto.transfers;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferApprovalRequest {
    private Boolean isAuthorized = false;
    private String note;
}
