package com.example.hospitalCrm.dtos.MedicineDto;


import com.example.hospitalCrm.type.MedicineCategory;
import com.example.hospitalCrm.type.MedicineDosageForm;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineRequest {

    private String medicineFullName;

    private String medicineGenericName;

    private MedicineDosageForm dosageForm;

    private List<MedicineCategory> medicineCategories;

    private String manufacturer;

    private String strengthMg_Ml;

}
