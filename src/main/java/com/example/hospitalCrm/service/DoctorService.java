package com.example.hospitalCrm.service;

import com.example.hospitalCrm.dtos.DoctorDto.DoctorAppointmentViewResponse;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorResponse;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorUpdateRequest;
import com.example.hospitalCrm.type.AppointmentStatus;

import java.util.List;

public interface DoctorService {

    DoctorResponse setAvailability(Long docId, Boolean availability);

    List<DoctorAppointmentViewResponse>  listAllAppointments(Long docId);

    DoctorAppointmentViewResponse updateAppointmentStatus(Long docId, Long appointmentId, AppointmentStatus appointmentStatus);

    DoctorResponse updateDoctor(Long docId, DoctorUpdateRequest doctorUpdateRequest);



}
