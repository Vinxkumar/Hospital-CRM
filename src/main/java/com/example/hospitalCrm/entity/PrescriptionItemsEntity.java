package com.example.hospitalCrm.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prescription_items")
public class PrescriptionItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionItemId;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private PrescriptionEntity prescription;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private MedicineEntity medicine;

    private String medicineDosage;

    private String prescriptionFrequency;

    private Long prescriptionDurationInDays;

    private String doctorNote;

}
