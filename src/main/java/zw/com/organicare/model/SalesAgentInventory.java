/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

import lombok.*;
import zw.com.organicare.constants.Branch;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "salesAgentInventory")
public class SalesAgentInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesAgentInventoryId;

    private int openingStock;
    private int closingStock;
    private int stockIn;
    @Column(name = "sold")
    private int numberOfProductsSold;
    @Column(name = "variance")
    private int numberOfProductsFreelyGiven;

    private LocalDate receivedDate;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sales_agentId", referencedColumnName = "userId")
    private User receivedBy; // Sales Agent

    @ManyToOne
    @JoinColumn(name = "issuedBy", referencedColumnName = "userId")
    private User issuedBy; // Warehouse/Admin

    @Enumerated(EnumType.STRING)
    private Branch branch;
}
