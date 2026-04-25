package com.example.hospitalCrm.dtos.PrescriptionDto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionRequest {

    private Long appointmentId;

    private Long doctorId;

    private Long patientId;

    private String patientDiagnosis;

    private LocalDateTime issuedAt;



}
