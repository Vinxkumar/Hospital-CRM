package com.example.hospitalCrm.dtos.PrescriptionItemsDto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionItemsResponse {

    private String medicineName;

    private String medicineDosage;

    private String prescriptionFrequency;

    private Long prescriptionDurationInDays;

    private String doctorNote;

}
