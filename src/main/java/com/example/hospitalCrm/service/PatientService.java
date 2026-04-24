package com.example.hospitalCrm.service;

import com.example.hospitalCrm.dtos.PatientDto.PatientResponse;
import com.example.hospitalCrm.dtos.PatientDto.PatientUpdateRequest;

public interface PatientService {

    PatientResponse updatePatient(Long patientId, PatientUpdateRequest patientUpdateRequest);

}
