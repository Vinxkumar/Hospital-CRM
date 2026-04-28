package com.example.hospitalCrm.entity;


import com.example.hospitalCrm.type.DoctorDepartment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jdk.dynalink.linker.LinkerServices;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.internal.bytebuddy.implementation.bind.MethodDelegationBinder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "doctors")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UsersEntity user;

    private String doctorAlternativePhone;

    private String doctorSpecialization;

    private String doctorQualification;

    @Column(unique = true)
    private String doctorLicenceNo;

    private Boolean doctorAvailable;

    @Enumerated(EnumType.STRING)
    private DoctorDepartment doctorDepartment;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AppointmentEntity> appointments;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PrescriptionEntity> prescriptions;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    private  LocalDateTime updatedAt;

}
