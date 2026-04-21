package com.example.hospitalCrm.entity;


import com.example.hospitalCrm.type.DoctorDepartment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jdk.dynalink.linker.LinkerServices;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UsersEntity user;

    private String doctorAlternativePhone;

    private String doctorSpecialization;

    private String doctorQualification;

    private String doctorLicenceNo;

    private Boolean doctorAvailable;

    @Enumerated(EnumType.STRING)
    private DoctorDepartment doctorDepartment;

    @OneToMany(mappedBy = "doctor")
    private List<AppointmentEntity> appointments;

    @OneToMany(mappedBy = "doctor")
    private List<PrescriptionEntity> prescriptions;

    @OneToMany(mappedBy = "doctor")
    private List<DoctorAvailabilityEntity> doctorAvailability;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    private  LocalDateTime updatedAt;

}
