package com.example.hospitalCrm.service.serviceImpl;

import com.example.hospitalCrm.dtos.AppointmentDto.AppointmentRequest;
import com.example.hospitalCrm.dtos.AppointmentDto.AppointmentResponse;
import com.example.hospitalCrm.dtos.AppointmentDto.AppointmentUpdateRequest;
import com.example.hospitalCrm.entity.AppointmentEntity;
import com.example.hospitalCrm.entity.DoctorEntity;
import com.example.hospitalCrm.entity.PatientEntity;
import com.example.hospitalCrm.respository.AppointmentRespository;
import com.example.hospitalCrm.respository.DoctorRepository;
import com.example.hospitalCrm.respository.PatientRepository;
import com.example.hospitalCrm.service.AppointmentService;
import com.example.hospitalCrm.type.AppointmentStatus;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private  final PatientRepository patientRepository;
    private final AppointmentRespository appointmentRespository;
    private final DoctorRepository doctorRepository;


    @Transactional
    @Override
    public AppointmentResponse bookAppointment(Long patientId, AppointmentRequest request) {

        log.info("Fetching Patient with Id: {}", patientId);

        PatientEntity patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new UsernameNotFoundException("Patient not found"));

        log.info("Fetch Doctor with Id: {}", request.getDoctorId());

        DoctorEntity doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new UsernameNotFoundException("Doctor not found"));

        log.info("Fetching Availability of Doctor with Id: {}", request.getDoctorId());

        boolean validSlot = doctor.getDoctorAvailable();

        if (!validSlot) {
            throw new RuntimeException("Doctor not available");
        }

        log.info("Fetching Appointment Slot Availability For Patient with Id: {}", patientId);

        boolean isBooked = appointmentRespository.existsOverlap(
                request.getDoctorId(),
                request.getAppointmentDate(),
                request.getStartTime(),
                request.getEndTime()
        );

        if (isBooked) {
            throw new RuntimeException("Slot already booked");
        }

        log.info("Creating Appointment for Patient with Id: {} with Doctor with Id: {} ", patientId, request.getDoctorId());

        AppointmentEntity appointment = mapToEntity(request);

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentStatus(AppointmentStatus.BOOKED);

        final AppointmentEntity savedAppointment = appointmentRespository.save(appointment);

        return mapToAppointmentResponse(savedAppointment);
    }

    @Transactional
    @Override
    public AppointmentResponse updateAppointment(Long patientId, Long appointmentId, AppointmentUpdateRequest appointmentUpdateRequest) {

        log.info("Fetching Doctor with Id: {} appointed with Patient with Id; {} For Appointment with Id: {}", appointmentUpdateRequest.getDoctorId(), patientId, appointmentId);

        AppointmentEntity appointment = appointmentRespository.findByAppointmentIdAndPatientPatientIdAndDoctorDoctorId(appointmentId, patientId, appointmentUpdateRequest.getDoctorId());

        if(appointment == null) {
            throw new RuntimeException("Appointment Not Found for Patient Id: " + patientId);
        }

        log.info("Fecthing Doctor Availability for the provided Appointment Time Slot");

        if(!appointment.getDoctor().getDoctorAvailable()) {
            throw new RuntimeException("Doctor Not Available at this Time Slot");
        }

        log.info("Fetching Appointment Availability time Slot ");

        if(appointmentRespository.existsOverlap(
                appointmentUpdateRequest.getDoctorId(),
                appointmentUpdateRequest.getAppointmentDate(),
                appointmentUpdateRequest.getStartTime(),
                appointmentUpdateRequest.getEndTime()

        )) {
            throw new RuntimeException("Slot Already Booked ");
        }

        if(
                !appointmentUpdateRequest.getAppointmentDate().equals(appointment.getAppointmentDate())
        ) {
                appointment.setAppointmentDate(appointmentUpdateRequest.getAppointmentDate());
        }

        if(
                !appointmentUpdateRequest.getStartTime().equals(appointment.getStartTime())
        ) {
            appointment.setStartTime(appointmentUpdateRequest.getStartTime());
        }

        if(
                !appointmentUpdateRequest.getEndTime().equals(appointment.getEndTime())
        ) {
            appointment.setEndTime(appointmentUpdateRequest.getEndTime());
        }

        log.info("Updating Appointment with Id: {} for Patient with Id: {}", appointmentId, patientId);

//        appointment.setAppointmentStatus(Ap);
        final AppointmentEntity updatedAppointment = appointmentRespository.save(appointment);

        return mapToAppointmentResponse(updatedAppointment);
    }

    @Override
    public List<AppointmentResponse> listAllAppointments(Long patientId) {
        log.info("Fetching Patient With Id: {}", patientId);

        if(!patientRepository.existsById(patientId)) {
            throw new UsernameNotFoundException("Patient Not Found with Id: " + patientId);
        }

        return mapToAppointmentResponseList(appointmentRespository.findByPatientPatientId(patientId));

//        return List.of();

    }

    @Override
    public AppointmentResponse fetchAppointmentById(Long patientId, Long appointmentId) {
        log.info("Fetching Appointment with Id: {} for Patient with Id: {}",appointmentId, patientId);

        final AppointmentEntity appointment = appointmentRespository.findByAppointmentIdAndPatientPatientId(appointmentId, patientId);

        if(appointment == null) {
            throw new RuntimeException("Appointment or Patient not Found");
        }

        return mapToAppointmentResponse(appointmentRespository.findByAppointmentIdAndPatientPatientId(appointmentId, patientId));
    }

    @Transactional
    @Override
    public AppointmentResponse cancelAppointment(Long patientId, Long appointmentId) {
        log.info("Fetching Appointment with Id: {} for Patient with Id: {}",appointmentId, patientId);
        AppointmentEntity appointment = appointmentRespository.findByAppointmentIdAndPatientPatientId(appointmentId, patientId);

        if(appointment == null) {
            throw new RuntimeException("Either Appointment or Patient not Found");
        }


        log.info("Updating Appointment Status to Canceled");

        if(appointment.getAppointmentStatus().equals(AppointmentStatus.VISIT_COMPLETE)) {
            throw new IllegalArgumentException("Cannot Cancel Appointment ");
        }

        appointment.setAppointmentStatus(AppointmentStatus.CANCELED);

        final AppointmentEntity savedAppointment = appointmentRespository.save(appointment);

        return mapToAppointmentResponse(appointment);
    }



    // mappers
    protected AppointmentEntity mapToEntity(AppointmentRequest request) {
        return AppointmentEntity.builder()
                .appointmentDate(request.getAppointmentDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .message(request.getMessage())
                .build();
    }

    protected AppointmentResponse mapToAppointmentResponse(AppointmentEntity appointment) {
        return new AppointmentResponse(
                appointment.getPatient().getUser().getUserName(),
                appointment.getMessage(),
                appointment.getDoctor().getUser().getUserName(),
                appointment.getDoctor().getDoctorQualification(),
                appointment.getDoctor().getDoctorSpecialization(),
                appointment.getDoctor().getDoctorAvailable(),
                appointment.getAppointmentDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getAppointmentStatus()
        );
    }

    protected List<AppointmentResponse> mapToAppointmentResponseList(List<AppointmentEntity> appointmentEntities) {
        return appointmentEntities.stream().map(
                appointment -> mapToAppointmentResponse(appointment)
        ).toList();
    }

}
