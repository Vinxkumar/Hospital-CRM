package com.example.hospitalCrm.entity;

import com.example.hospitalCrm.type.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name="patient_id")
    private PatientEntity patient;

    private LocalDateTime appointmentDataAndTime;

    private String message;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @OneToOne(mappedBy = "appointment")
    private PrescriptionEntity prescription;

}
