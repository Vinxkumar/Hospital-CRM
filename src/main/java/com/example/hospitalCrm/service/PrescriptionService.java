package com.example.hospitalCrm.service;

import com.example.hospitalCrm.dtos.PrescriptionDto.PrescriptionRequest;
import com.example.hospitalCrm.dtos.PrescriptionDto.PrescriptionResponse;
import com.example.hospitalCrm.dtos.PrescriptionItemsDto.PrescriptionItemsRequest;

import java.util.List;

public interface PrescriptionService {

    PrescriptionResponse createPrescription(Long patientId, Long doctorId, Long appointmentId, PrescriptionRequest request, List<PrescriptionItemsRequest> prescriptionItemsRequests);

    List<PrescriptionResponse> fetchAllByPatientId(Long PatientId);

    List<PrescriptionResponse> fetchAllByDoctorId(Long docId);

    PrescriptionResponse fetchByAppointmentId(Long appointmentId);

}
