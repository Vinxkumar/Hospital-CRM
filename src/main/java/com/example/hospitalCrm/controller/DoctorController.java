package com.example.hospitalCrm.controller;


import com.example.hospitalCrm.dtos.DoctorDto.DoctorAppointmentViewResponse;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorResponse;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorUpdateRequest;
import com.example.hospitalCrm.service.serviceImpl.DoctorServiceImpl;
import com.example.hospitalCrm.type.AppointmentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/doctor/{docId}")
public class DoctorController {

    private final DoctorServiceImpl doctorService;

    @GetMapping("/appointment/all")
    public ResponseEntity<List<DoctorAppointmentViewResponse>> listAllAppointment(@PathVariable Long docId) {

        log.info("REST: Request to List All Appointments of Doctor with Id: {}", docId) ;

        return ResponseEntity.ok(doctorService.listAllAppointments(docId));
    }

    @PutMapping("/availability/{aval}")
    public ResponseEntity<DoctorResponse> setAvail(@PathVariable Long docId, @PathVariable Boolean aval) {

        log.info("REST: Request to Update Status for Doctor with Id: {}", docId);

        return ResponseEntity.ok(doctorService.setAvailability(docId, aval));
    }

    @PutMapping("/update")
    public ResponseEntity<DoctorResponse> updateDoc(@PathVariable Long docId, @RequestBody DoctorUpdateRequest doctorUpdateRequest) {

        log.info("REST: Request to update Doctor with Id: {}", docId);

        return ResponseEntity.ok(doctorService.updateDoctor(docId, doctorUpdateRequest));
    }


    @PutMapping("/appointment/{appId}/status/{status}")
    public ResponseEntity<DoctorAppointmentViewResponse> updateAppStatus(@PathVariable Long docId, @PathVariable Long appId, @PathVariable AppointmentStatus status) {

        log.info("REST: Request to Update Appointment Status");

        return ResponseEntity.ok(doctorService.updateAppointmentStatus(docId, appId, status));
    }


}
