/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Inventory")
public class StockOveral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventorId;

    private int openingStock;
    private int closingStock;
    private int stockIn;
    private int stockOut;
    private String reasonForStockOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "issuedBy", nullable = false)
    private User issuedBy;

    @ManyToOne
    @JoinColumn(name = "receivedBy", nullable = false)
    private User receivedBy;


}
