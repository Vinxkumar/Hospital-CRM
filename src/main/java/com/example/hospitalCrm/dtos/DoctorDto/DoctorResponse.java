package com.example.hospitalCrm.dtos.DoctorDto;


import lombok.*;

import java.util.List;



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {

    String jwtToken;

    private String doctorName;

    private String doctorPhone;

    private String doctorAlternativePhone;

    private String doctorSpecialization;

    private String doctorQualification;

    private String doctorLicenceNo;

    private Boolean doctorAvailable;

//    private List<DoctorAvailabilityResponse> doctorAvailability;
}
