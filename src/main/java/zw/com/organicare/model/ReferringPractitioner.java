/**
 * @author : tadiewa
 * date: 9/11/2025
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
@Table(name = "Agent")
public class ReferringPractitioner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agent_Id")
    private Long rp_Id;
    private double commission_rate;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

}
