/**
 * @author : tadiewa
 * date: 9/12/2025
 */


package zw.com.organicare.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "request_forms")
public class RequestForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestFormId;

    private String medicalAid;
    private String lmp;
    private String clinicalHistory;
    private String referringDoctor;
    private Date date;

    @ElementCollection
    @CollectionTable(name = "ultrasound_requests", joinColumns = @JoinColumn(name = "request_form_id"))
    @Column(name = "ultrasound_type")
    private List<String> ultraSoundRequestedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_card_id", nullable = false)
    private PatientCard patientCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by")
    private User requestedBy;

}
