package com.example.hospitalCrm.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "medicine_inventory",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"batchNo", "manufacturer"}, name = "Batch Number Should be Unique")
        }
)
public class MedicineInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long inventoryId;

    @OneToOne
    @JoinColumn(name = "medicine_id")
    private MedicineEntity medicine;

    private String batchNo;

    private String manufacturer;

    private Long pricePer;

    private Long totalQuantity;

    private Long inStock;

    private LocalDateTime manufacturedAt;
    private LocalDateTime expireAt;

}
