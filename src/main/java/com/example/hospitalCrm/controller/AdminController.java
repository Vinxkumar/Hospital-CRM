package com.example.hospitalCrm.controller;


import com.example.hospitalCrm.configuration.GetCurrentUser;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorRequest;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorResponse;
import com.example.hospitalCrm.dtos.KeyMetricsResponse;
import com.example.hospitalCrm.dtos.MedicineDto.AddMedicineRequest;
import com.example.hospitalCrm.dtos.MedicineDto.MedicineResponse;
import com.example.hospitalCrm.dtos.PatientDto.PatientRequest;
import com.example.hospitalCrm.dtos.PatientDto.PatientResponse;
import com.example.hospitalCrm.dtos.UserDto.UserRequest;
import com.example.hospitalCrm.dtos.UserDto.UserResponse;
import com.example.hospitalCrm.service.serviceImpl.AdminServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServiceImp adminService;
    private final GetCurrentUser currentUser;

    //Post Mapping

    @PostMapping("/new/account")
    public ResponseEntity<UserResponse> createNewAdminAccount(@RequestBody UserRequest userRequest) {

        log.warn("REST: request for creating new Admin Account");
        return ResponseEntity.ok(adminService.createNewAdminUser(userRequest));
    }

    @PostMapping("/new/doctor")
    public ResponseEntity<DoctorResponse> createNewDoctorAccount(@RequestBody DoctorRequest doctorRequest) {

        log.warn("REST: request for creating new Doctor Account");

        return ResponseEntity.ok(adminService.createNewDoctor(currentUser.getCurrentId(), doctorRequest));
    }

    @PostMapping("/new/patient")
    public ResponseEntity<PatientResponse> createNewPatientAccount(@RequestBody PatientRequest patientRequest) {

        log.warn("REST: request to create new Patient Account by Admin with Id: {}", currentUser.getCurrentId());
        return ResponseEntity.ok(adminService.createNewPatient(currentUser.getCurrentId(), patientRequest));
    }

    @PostMapping("/new/medicine")
    public ResponseEntity<MedicineResponse> addNewMedicine(@RequestBody AddMedicineRequest addMedicineRequest) {

        log.warn("REST: Request to Add Medicine By Admin with Id: {}", currentUser.getCurrentId());

        return ResponseEntity.ok(adminService.addMedicine(currentUser.getCurrentId(), addMedicineRequest));
    }



    // Get Mapping

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> fetchAdminById(@PathVariable Long id) {

        log.info("REST: request to fetch Admin User with Id: {}", id);
        return ResponseEntity.ok(adminService.fetchByAdminUserId(id));
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<DoctorResponse> fetchByDoctorId(@PathVariable Long id) {

        log.info("REST: request for Fetching Doctor By Id: {}", id);
        return ResponseEntity.ok(adminService.fetchByDoctorId(id));
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<PatientResponse> fetchByPatientId(@PathVariable Long id) {
        log.info("REST: request to Fetch Patient with Id: {}", id);

        return ResponseEntity.ok(adminService.fetchByPatientId(id));
    }

    @GetMapping("/pharma/{id}")
    public ResponseEntity<UserResponse> fetchByPharmaId(@PathVariable Long id) {
        log.info("REST: request to fetch by Pharma Id: {}", id);
        return ResponseEntity.ok(adminService.fetchByPharmaId(id));
    }

    @GetMapping("/medicine/{medId}")
    public ResponseEntity<MedicineResponse>  fetchByMedId(@PathVariable Long medId) {

        log.info("REST: request to fetch Medicine with Id: {} by Admin with Id: {}", medId, currentUser.getCurrentId());

        return ResponseEntity.ok(adminService.fetchByMedicineId(currentUser.getCurrentId(), medId));

    }

    @GetMapping("/medicine/all")
    public ResponseEntity<List<MedicineResponse>> fetchAllMedicine() {

        log.info("REST: request to list all medicines by Admin with Id: {}",currentUser.getCurrentId());

        return ResponseEntity.ok(adminService.fetchAllMedicine(currentUser.getCurrentId()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserRequest>> listAllAdminUsers() {
        log.info("REST: request to List all the admin Users");

        return ResponseEntity.ok(adminService.listAllAdmin());
    }

    @GetMapping("/doctor/all")
    public ResponseEntity<List<DoctorResponse>> listAllDoctors() {

        log.info("REST: request to List all the Doctor Users");

        return ResponseEntity.ok(adminService.listAllDoctor());
    }

    @GetMapping("/patient/all")
    public ResponseEntity<List<PatientResponse>> listAllPatients() {

        log.info("REST: request for Listing All the Patients");

        return ResponseEntity.ok(adminService.listAllPatient());
    }

    @GetMapping("/pharma/all")
    public ResponseEntity<List<UserRequest>> listAllPharma() {

        log.info("REST: request for listing all Pharma Staffs");

        return ResponseEntity.ok(adminService.listAllPharma());
    }



    // Delet Mapping

    @DeleteMapping("/{adminId}")
    public ResponseEntity<String> deleteByAdminId(@PathVariable Long adminId) {
        log.warn("REST: request to Delete Admin with Id: {}", adminId);

        adminService.deleteAdminUser(adminId);

        return ResponseEntity.ok("Deleted Admin with Id: "+ adminId);
    }

    @DeleteMapping("/doctor/{doctorId}")
    public ResponseEntity<String> deleteByDoctorId(@PathVariable Long docId) {
        log.warn("REST: request to delete Doctor with Id: {}", docId);

        adminService.deleteDoctor(docId);

        return ResponseEntity.ok("Deleted Doctor with Id: "+ docId);
    }

    @DeleteMapping("/patient/{patientId}")
    public ResponseEntity<String> deleteByPatientId(@PathVariable Long patientId) {
        log.warn("REST: request to delete Patient with Id: {}", patientId);

        adminService.deletePatient(patientId);

        return ResponseEntity.ok("Deleted Patient with Id");
    }



    @DeleteMapping("/medicine/{medicineId}")
    public ResponseEntity<String> deleteByMedicineId(@PathVariable Long medicineId) {

        log.warn("REST: request to delete Medicine with Id: {} by Admin with Id: {}", medicineId, currentUser.getCurrentId());
        adminService.removeMedicine(currentUser.getCurrentId(), medicineId);
        return ResponseEntity.ok("Medicine with Id" + medicineId + " Deleted Successfully");
    }


    @DeleteMapping("/all")
    public ResponseEntity<String > deleteAllAdmin() {

        log.warn("REST: request to Delete All Admin Users");
        adminService.deleteAllAdmin();
        return ResponseEntity.status(HttpStatus.OK).body("Deleted All Admin Users");
    }


    @DeleteMapping("/doctor/all")
    public ResponseEntity<String> deleteAllDoctor() {
        log.warn("REST: request to delete All Doctors...!");

        adminService.deleteAllDoctors();

        return ResponseEntity.ok("Deleted All Doctor Users..!");
    }

    @DeleteMapping("/patient/all")
    public ResponseEntity<String> deleteAllPatient() {
        log.warn("REST: request to delete All Patients..!");

        adminService.deleteAllPatients();

        return ResponseEntity.ok("Deleted All Patients");
    }

    @DeleteMapping("/pharma/all")
    public ResponseEntity<String> deleteAllPharma() {
        log.warn("REST: request to delete all Pharma Users");

        adminService.deleteAllPharma();

        return ResponseEntity.ok("Deleted All Pharma Staffs");
    }


    @GetMapping("/keyMetrics")
    public ResponseEntity<KeyMetricsResponse> response() {

        log.info("REST: request for KeyMetrics from FrontEnd");

        return ResponseEntity.ok(adminService.keyMetricsResponse());
    }


}
