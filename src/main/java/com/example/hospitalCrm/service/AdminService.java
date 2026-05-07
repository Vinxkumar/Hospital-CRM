package com.example.hospitalCrm.service;

import com.example.hospitalCrm.dtos.DoctorDto.DoctorRequest;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorResponse;
import com.example.hospitalCrm.dtos.KeyMetricsResponse;
import com.example.hospitalCrm.dtos.MedicineDto.AddMedicineRequest;
import com.example.hospitalCrm.dtos.MedicineDto.MedicineResponse;
import com.example.hospitalCrm.dtos.MedicineInventoryDto.UpdateInventoryRequest;
import com.example.hospitalCrm.dtos.PatientDto.PatientRequest;
import com.example.hospitalCrm.dtos.PatientDto.PatientResponse;
import com.example.hospitalCrm.dtos.UserDto.UserRequest;
import com.example.hospitalCrm.dtos.UserDto.UserResponse;

import java.util.List;

public interface AdminService {
    UserResponse createNewAdminUser(UserRequest userRequest);
    DoctorResponse createNewDoctor(Long adminId, DoctorRequest doctorRequest);
    UserResponse createNewPharma(Long admId, UserRequest userRequest);
    PatientResponse createNewPatient(Long adminId, PatientRequest patientRequest);

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

    MedicineResponse addMedicine(Long adminId, AddMedicineRequest addMedicineRequest);
    MedicineResponse updateMedicine(Long adminId, Long medicineId, UpdateInventoryRequest updateInventoryRequest);
    MedicineResponse fetchByMedicineId(Long adminId, Long medicineId);
    List<MedicineResponse> fetchAllMedicine(Long adminId);
    void removeMedicine(Long adminId, Long medicineId);
    void removeAllMedicine(Long adminId);

    KeyMetricsResponse keyMetricsResponse();
}
