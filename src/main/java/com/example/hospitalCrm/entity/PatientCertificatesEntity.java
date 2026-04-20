package com.example.hospitalCrm.entity;

import com.example.hospitalCrm.type.PatientCertificateType;
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
@Table(name = "patient_certificate")
public class PatientCertificatesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private AppointmentEntity appointment;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorEntity doctor;

    @Enumerated(EnumType.STRING)
    private PatientCertificateType certificateType;  // FIT_TO_WORK, MEDICAL_LEAVE etc

    @Column(nullable = false)
    private String fileUrl;   // path to PDF file

    private String notes;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime issuedAt;
}
