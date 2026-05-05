package com.example.hospitalCrm.dtos.MedicineInventoryDto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {


    private Long medicineId;

    private Long pricePer;

    private Long inStock;

    private String manufacturer;

    private LocalDateTime manufacturedAt;

    private LocalDateTime expireAt;

}
