package com.example.hospitalCrm.dtos.PrescriptionItemsDto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionItemsRequest {

    private Long prescriptionId;

    private Long medicineId;

    private String medicineDosage;

    private String prescriptionFrequency;

    private Long prescriptionDurationInDays;

    private String doctorNote;

}
