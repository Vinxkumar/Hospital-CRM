package com.example.hospitalCrm.dtos.AppointmentDto;

import com.example.hospitalCrm.type.AppointmentStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {

    private String message;

    private Long doctorId;

    private LocalDate appointmentDate;

    private LocalTime startTime;

    private LocalTime endTime;

}
