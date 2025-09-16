/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productCode;
    private String name;
    private String description;
    private Double price;
    private String category;
    private boolean isActive;

}
