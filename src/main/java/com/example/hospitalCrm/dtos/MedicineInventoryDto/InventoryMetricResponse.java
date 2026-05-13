package com.example.hospitalCrm.dtos.MedicineInventoryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryMetricResponse {

    private Long id;

    private String name;

    private Long inStock;

}
