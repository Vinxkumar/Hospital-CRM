package com.example.hospitalCrm.entity;


import com.example.hospitalCrm.type.MedicineDevliverStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pharmacy")
public class PharmacyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pharmacyId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UsersEntity user;

    @ManyToOne
    @JoinColumn(name = "patient_id", unique = true, nullable = false)
    private PatientEntity patient;

    @OneToOne
    @JoinColumn(name = "prescription_id", unique = true, nullable = false)
    private PrescriptionEntity prescription;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime orderCreatedAt;

    @Enumerated(EnumType.STRING)
    private MedicineDevliverStatus deliverStatus;

    private LocalDateTime deliveredAt;

}
