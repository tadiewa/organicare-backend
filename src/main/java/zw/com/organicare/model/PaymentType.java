/**
 * @author : tadiewa
 * date: 9/17/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentTypeId;

    @Column(nullable = false, unique = true)
    private String name;   // e.g. "USD Cash", "Ecocash", "Bank Transfer", "Mukuru"

    private boolean isActive = true;
}

