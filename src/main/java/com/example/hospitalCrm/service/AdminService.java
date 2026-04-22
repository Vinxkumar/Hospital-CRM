package com.example.hospitalCrm.service;

import com.example.hospitalCrm.dtos.DoctorDto.DoctorRequest;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorResponse;
import com.example.hospitalCrm.dtos.PatientDto.PatientRequest;
import com.example.hospitalCrm.dtos.PatientDto.PatientResponse;
import com.example.hospitalCrm.dtos.UserDto.UserRequest;
import com.example.hospitalCrm.dtos.UserDto.UserResponse;

import java.util.List;

public interface AdminService {
    UserResponse createNewAdminUser(UserRequest userRequest);
    DoctorResponse createNewDoctor( DoctorRequest doctorRequest);
    UserResponse createNewPharma(UserRequest userRequest);
    PatientResponse createNewPatient(PatientRequest patientRequest);

    UserResponse fetchByAdminUserId(Long id);
    DoctorResponse fetchByDoctorId(Long id);
    UserResponse fetchByPharmaId(Long id);
    PatientResponse fetchByPatientId(Long id);

    List<UserRequest> listAllAdmin();
    List<DoctorResponse> listAllDoctor();
    List<UserRequest> listAllPharma();
    List<PatientResponse> listAllPatient();

    void deleteAdminUser(Long adminId);
    void deleteDoctor(Long doctorId);
    void deletePharma(Long pharmaId);
    void deletePatient(Long patientId);

    void deleteAllAdmin();
    void deleteAllDoctors();
    void deleteAllPharma();
    void deleteAllPatients();
}
