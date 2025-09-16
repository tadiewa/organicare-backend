/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;

    private String saleNumber; // auto-generated e.g. "SALE-0001"
    private Date saleDate;
    private Double totalAmount;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleLine> saleLines = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_agent_id", nullable = false)
    private User salesAgent;
}
