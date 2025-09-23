/**
 * @author : tadiewa
 * date: 9/15/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private User  doctor;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    private Double weight;
    private Double height;
    private Boolean isApproved = false;
    private Boolean isCompleted = false;
    private LocalDateTime appointmentDate;
    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

}
