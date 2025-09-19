/**
 * @author : tadiewa
 * date: 9/16/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = true)
    private Patient patient;

    @Column(precision = 19, scale = 4)
    private BigDecimal totalAmountDue = BigDecimal.ZERO;

    @Column(precision = 19, scale = 4)
    private BigDecimal totalPaid = BigDecimal.ZERO;

    @Column(precision = 19, scale = 4)
    private BigDecimal changeGiven = BigDecimal.ZERO;

    @Column(precision = 19, scale = 4)
    private BigDecimal uncollectedChange = BigDecimal.ZERO;

    private LocalDateTime saleDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "sales_agent_id")
    private User agent;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SaleLine> saleLines = new ArrayList<>();

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();
}

