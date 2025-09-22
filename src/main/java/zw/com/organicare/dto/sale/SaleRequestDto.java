/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.dto.sale;

import lombok.*;
import zw.com.organicare.constants.Branch;
import zw.com.organicare.dto.payment.PaymentRequestDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleRequestDto {

    private Long patientId;
   // private Branch branch;
    private boolean changeProvided;
    private List<SaleLineRequestDto> saleLines;
    private List<PaymentRequestDto> payments;

}
