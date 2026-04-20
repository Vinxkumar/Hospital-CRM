package com.example.hospitalCrm.entity;


import com.example.hospitalCrm.type.MedicineCategory;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private String medicineManufacturer;

    private LocalDate medicineExpireData;

    private Long medicineStockInNumber;

    private Long medicinePerUnitPrice;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;



    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
