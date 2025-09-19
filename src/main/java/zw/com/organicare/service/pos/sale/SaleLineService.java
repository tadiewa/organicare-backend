/**
 * @author : tadiewa
 * date: 9/19/2025
 */

package zw.com.organicare.service.pos.sale;

import zw.com.organicare.dto.sale.SaleLineRequestDto;
import zw.com.organicare.dto.sale.SaleLineResponseDto;

import java.util.List;

public interface SaleLineService {
    SaleLineResponseDto addSaleLine(Long saleId, SaleLineRequestDto request);
    List<SaleLineResponseDto> getSaleLinesBySale(Long saleId);
}
