/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "uncollected_change")
public class UncollectedChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uncollectedChangeId;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    private BigDecimal changeAmount;
    private boolean resolved = false;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String notes;
    private String receiptNumber;
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = true)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "sales_agent_id", nullable = true)
    private User salesAgent;

    @Column(name = "given_by", nullable = true)
    private String givenBy;
}

