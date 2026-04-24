package com.example.hospitalCrm.dtos.DoctorDto;


import com.example.hospitalCrm.type.DoctorDepartment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateRequest {

    private String phone;

    private String emergencyPhone;

    private String specialization;

    private DoctorDepartment department;

}
