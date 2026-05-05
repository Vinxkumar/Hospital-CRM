package com.example.hospitalCrm.entity;


import com.example.hospitalCrm.type.MedicineDeliverStatus;
import com.example.hospitalCrm.type.PaymentMode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="prescription_bill")
public class PrescriptionBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private AppointmentEntity appointment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id")
    private PrescriptionEntity prescription;

    private Long totalBill;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @Enumerated(EnumType.STRING)
    private MedicineDeliverStatus status;

}
