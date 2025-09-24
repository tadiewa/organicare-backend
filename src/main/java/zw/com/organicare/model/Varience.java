/**
 * @author : tadiewa
 * date: 9/24/2025
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
@Table(name = "varience")
public class Varience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long varienceId;
    String reason;
    Double quantity;
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
