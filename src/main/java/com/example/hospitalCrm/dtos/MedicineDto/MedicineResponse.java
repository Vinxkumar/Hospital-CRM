package com.example.hospitalCrm.dtos.MedicineDto;


import com.example.hospitalCrm.dtos.MedicineInventoryDto.InventoryResponse;
import com.example.hospitalCrm.type.MedicineCategory;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineResponse {

    private String medicineFullName;

    private List<MedicineCategory> medicineCategories;

    private String strengthMg_Ml;

    private InventoryResponse inventoryResponse;

}
