package com.example.hospitalCrm.entity;


import com.example.hospitalCrm.type.PatientBloodGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patients")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UsersEntity user;

    @Column(unique = true)
    private String emergencyPhone;

    private String patientAddress;

    @Enumerated(EnumType.STRING)
    private PatientBloodGroup patientBloodGroup;

    @OneToMany(mappedBy = "patient")
    private List<AppointmentEntity> appointments;

    @OneToMany(mappedBy = "patient")
    private List<PrescriptionEntity> prescriptions;
}
