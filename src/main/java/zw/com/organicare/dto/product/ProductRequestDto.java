/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.dto.product;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private String name;
    private String description;
    private Double price;
    private String category;
    private boolean isActive;
}
