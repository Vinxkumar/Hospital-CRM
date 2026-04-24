package com.example.hospitalCrm.dtos.DoctorDto;


import com.example.hospitalCrm.type.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAppointmentViewResponse {

    private String patientName;

    private String message;

    private LocalDate appointmentDate;

    private LocalTime appointmentStartTime;

    private LocalTime appointmentEndTime;

    private AppointmentStatus appointmentStatus;

}
