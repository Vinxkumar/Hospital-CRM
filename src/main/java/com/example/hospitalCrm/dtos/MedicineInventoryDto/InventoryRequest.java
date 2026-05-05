package com.example.hospitalCrm.dtos.MedicineInventoryDto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {

    private Long medicineId;

    private String batchNo;

    private String manufacturer;

    private Long perPrice;

    private Long totalQuantity;

    private Long inStock;

    private LocalDateTime manufacturedAt;

    private LocalDateTime expireAt;


}
