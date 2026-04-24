package com.example.hospitalCrm.service;

import com.example.hospitalCrm.dtos.AppointmentDto.AppointmentRequest;
import com.example.hospitalCrm.dtos.AppointmentDto.AppointmentResponse;
import com.example.hospitalCrm.dtos.AppointmentDto.AppointmentUpdateRequest;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse bookAppointment(Long patientId, AppointmentRequest appointmentRequest);
    AppointmentResponse updateAppointment(Long patientId, Long appointmentId, AppointmentUpdateRequest appointmentUpdateRequest);

    List<AppointmentResponse> listAllAppointments(Long patientId);

    AppointmentResponse fetchAppointmentById(Long patientId, Long appointmentId);

    AppointmentResponse cancelAppointment(Long patientId, Long appointmentId);

}
