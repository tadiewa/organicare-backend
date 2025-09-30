/**
 * @author : tadiewa
 * date: 9/11/2025
 */


package zw.com.organicare.model;


import jakarta.persistence.*;
import lombok.*;
import zw.com.organicare.constants.Gender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    private String fullName;
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String contactInfo;
    private String address;
    @OneToOne(fetch = FetchType.LAZY , cascade=CascadeType.ALL)
    @JoinColumn(name = "emergency_contact_id", referencedColumnName = "emergency_contact_id",nullable = true)
    private EmergencyContact emergencyContact;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rp_Id", referencedColumnName = "agent_Id",nullable = true)
    private ReferringPractitioner referringPractitioner;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientCard> patientCards = new ArrayList<>();


}
