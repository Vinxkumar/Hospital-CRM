package com.example.hospitalCrm.entity;


import com.example.hospitalCrm.type.MedicineDeliverStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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


    @ManyToOne
    @JoinColumn(name = "prescription_id", unique = true, nullable = false)
    private PrescriptionEntity prescription;


//    @OneToMany(mappedBy = "pharmacy")
//    private List<MedicineInventory> medicineInventoryList;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime orderCreatedAt;

    @Enumerated(EnumType.STRING)
    private MedicineDeliverStatus deliverStatus;

    private LocalDateTime deliveredAt;

}
