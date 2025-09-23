/**
 * @author : tadiewa
 * date: 9/12/2025
 */


package zw.com.organicare.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_Card")
public class PatientCard{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientCardId;
    private String cardNumber;
    private String patientHistory;
    private String familyHistory;
    private String socialHistory;
    private String presentingComplaints;
    private String diagnosis;
    private String remarks;
    private String treatmentPlan;
    private String duration;
    private Date   reviewDate;
    private String bp;
    private Double weight;
    private Double height;
    private String patientSignature;
    private String doctorSignature;
    private Boolean ultrasoundRequestFlag;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId", referencedColumnName = "patientId",nullable = false)
    private Patient patient;

    @OneToMany(mappedBy = "patientCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestForm> requestForms = new ArrayList<>();



}
