package com.example.hospitalCrm.entity;


import com.example.hospitalCrm.type.MedicineCategory;

import com.example.hospitalCrm.type.MedicineDosageForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medicines")
public class MedicineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicineId;

    private String medicineFullName;

    private String medicineGenericName;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<MedicineCategory> category;

    @Enumerated(EnumType.STRING)
    private MedicineDosageForm medicineDosageForm;

    private String medicineManufacturer;

    private String strengthMg_Ml;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @OneToOne(mappedBy = "medicine")
    private MedicineInventory medicineInventories;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
