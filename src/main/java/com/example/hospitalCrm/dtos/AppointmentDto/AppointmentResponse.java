package com.example.hospitalCrm.dtos.AppointmentDto;


import com.example.hospitalCrm.type.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {


    private String patientName;

    private String message;

    private String doctorName;
//
    private String doctorQualification;

    private String doctorSpecialization;

    private Boolean doctorAvailable;


    private LocalDate appointmentDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private AppointmentStatus appointmentStatus;


}
