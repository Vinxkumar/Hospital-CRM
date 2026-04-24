package com.example.hospitalCrm.controller;

import com.example.hospitalCrm.dtos.AppointmentDto.AppointmentRequest;
import com.example.hospitalCrm.dtos.AppointmentDto.AppointmentResponse;
import com.example.hospitalCrm.dtos.AppointmentDto.AppointmentUpdateRequest;
import com.example.hospitalCrm.service.serviceImpl.AppointmentServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/patient/{id}/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentResponse>> listAllAppointments(@PathVariable Long id) {

        log.info("REST: Request to list all the appointments from patient with Id: {} ", id);

        return ResponseEntity.ok(appointmentService.listAllAppointments(id));

    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> fetchByAppointmentId(@PathVariable Long id, @PathVariable Long appointmentId) {

        log.info("REST: Request to Fetch Appointment with Id: {} by Patient with Id: {}", appointmentId, id);

        return ResponseEntity.ok(appointmentService.fetchAppointmentById(id, appointmentId));

    }

    @PostMapping("/new")
    public ResponseEntity<AppointmentResponse> newAppointment(@PathVariable Long id, @RequestBody AppointmentRequest appointmentRequest) {

        log.info("REST: Request to book New Appointment by Patient with Id: {}", id);

        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.bookAppointment(id, appointmentRequest));

    }

    @PutMapping("/update/{appointmentId}")
    public ResponseEntity<AppointmentResponse> updateAppointment(@PathVariable Long id, @PathVariable Long appointmentId, @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {

        log.info("REST: Request to update Appointment with Id; {} for Patient with Id; {}", appointmentId, id);

        return ResponseEntity.ok(appointmentService.updateAppointment(id, appointmentId, appointmentUpdateRequest));

    }


    @PutMapping("/cancel/{appointmentId}")
    public ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable Long id, @PathVariable Long appointmentId) {

        log.info("REST: Request to Cancel Appointment with Id: {} for Patient with Id: {}", appointmentId ,id);

        return ResponseEntity.ok(appointmentService.cancelAppointment(id, appointmentId));
    }

}
