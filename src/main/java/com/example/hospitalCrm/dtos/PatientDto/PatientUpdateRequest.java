package com.example.hospitalCrm.dtos.PatientDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientUpdateRequest {

    private String patientPhone;

    private String emergencyPhone;

    private String patientAddress;

}
