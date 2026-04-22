package com.example.hospitalCrm.dtos.PatientDto;


import com.example.hospitalCrm.dtos.UserDto.UserRequest;
import com.example.hospitalCrm.type.PatientBloodGroup;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {

    private UserRequest user;

    private String emergencyPhone;

    private String patientAddress;

    private PatientBloodGroup patientBloodGroup;


}
