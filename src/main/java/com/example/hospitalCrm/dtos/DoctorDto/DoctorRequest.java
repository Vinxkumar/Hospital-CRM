package com.example.hospitalCrm.dtos.DoctorDto;


import com.example.hospitalCrm.dtos.DoctorAvailabilityDto.DoctorAvailabilityResponse;

import com.example.hospitalCrm.dtos.UserDto.UserRequest;
import com.example.hospitalCrm.type.DoctorDepartment;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequest {

    private UserRequest user;

    private String doctorAlternativePhone;

    private String doctorSpecialization;

    private String doctorQualification;

    private String doctorLicenceNo;

    private Boolean doctorAvailable;

    @Enumerated(EnumType.STRING)
    private DoctorDepartment doctorDepartment;

    private List<DoctorAvailabilityResponse> doctorAvailability;

}
