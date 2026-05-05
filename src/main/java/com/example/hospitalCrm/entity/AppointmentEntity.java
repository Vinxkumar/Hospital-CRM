package com.example.hospitalCrm.entity;

import com.example.hospitalCrm.type.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id")
    private PatientEntity patient;


    private LocalDate appointmentDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @CreationTimestamp
    private LocalDateTime appointmentCreatedAt;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @OneToOne(mappedBy = "appointment")
    private PrescriptionEntity prescription;

    @OneToOne(mappedBy = "appointment")
    private PrescriptionBill prescriptionBill;

}
