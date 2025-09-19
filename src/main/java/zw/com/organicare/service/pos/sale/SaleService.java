/**
 * @author : tadiewa
 * date: 9/18/2025
 */

package zw.com.organicare.service.pos.sale;

import zw.com.organicare.dto.sale.SaleRequestDto;
import zw.com.organicare.dto.sale.SaleResponseDto;

public interface SaleService {
    SaleResponseDto createSale(SaleRequestDto request);
    SaleResponseDto getSale(Long saleId);
}
