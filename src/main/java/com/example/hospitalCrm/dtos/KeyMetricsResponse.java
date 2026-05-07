package com.example.hospitalCrm.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyMetricsResponse {

    private Long MedicineTotalCount;

    private Long PatientTotalCount;

    private Long DoctorTotalCount;

}
