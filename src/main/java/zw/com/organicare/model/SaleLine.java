/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sale_lines")
public class SaleLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleLineId;

    private Integer quantity;
    private Double price;
    private Double lineTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
