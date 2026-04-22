package com.example.hospitalCrm.dtos.PatientDto;


import com.example.hospitalCrm.dtos.AppointmentDto.AppointmentResponse;
import com.example.hospitalCrm.dtos.PatientCertificate.PatientCertificateResponse;
import com.example.hospitalCrm.dtos.PrescriptionDto.PrescriptionResponse;
import com.example.hospitalCrm.type.PatientBloodGroup;
import jakarta.annotation.Nullable;
import lombok.*;

import java.util.List;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {

    private String jwtToken;

    private String patientName;

    private String patientEmail;

    private String patientPhone;

    private String patientEmergencyPhone;

    private PatientBloodGroup patientBloodGroup;

    private String patientAddress;

    @Nullable
    private List<AppointmentResponse> patientAppointments;
    private List<PrescriptionResponse> patientPrescriptions;
    private List<PatientCertificateResponse> patientCertificates;
}
