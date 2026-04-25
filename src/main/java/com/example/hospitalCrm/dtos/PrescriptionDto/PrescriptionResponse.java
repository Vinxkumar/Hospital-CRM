package com.example.hospitalCrm.dtos.PrescriptionDto;

import com.example.hospitalCrm.dtos.PrescriptionItemsDto.PrescriptionItemsResponse;
import com.example.hospitalCrm.type.DoctorDepartment;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionResponse {

    private LocalDate appointmentDate;

    private String doctorName;

    private String doctorSpecialization;

    private DoctorDepartment doctorDepartment;

    private LocalTime startTime;

    private LocalTime endTime;

    private String patientDiagnosis;

    private List<PrescriptionItemsResponse> prescriptionItems;

}
