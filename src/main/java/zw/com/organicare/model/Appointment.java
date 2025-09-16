/**
 * @author : tadiewa
 * date: 9/15/2025
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
@Table(name = "Appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    private String appointmentNumber;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "doctor_user_id")
    private User   doctor;
    private String branch;
    private Double weight;
    private Double height;
    private String isApproved;
    private String isCompleted;
    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

}
