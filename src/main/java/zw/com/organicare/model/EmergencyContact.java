/**
 * @author : tadiewa
 * date: 9/12/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;
import lombok.*;
import zw.com.organicare.constants.Relationship;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_emergency_contact")

public class EmergencyContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emergency_contact_id")
    private Long emergencyContactId;
    private String name;
    @Enumerated(EnumType.STRING)
    private Relationship relationship;
    private String phone;

}
