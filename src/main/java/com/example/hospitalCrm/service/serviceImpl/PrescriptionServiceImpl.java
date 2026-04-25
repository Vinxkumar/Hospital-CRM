package com.example.hospitalCrm.service.serviceImpl;

import com.example.hospitalCrm.dtos.PrescriptionDto.PrescriptionRequest;
import com.example.hospitalCrm.dtos.PrescriptionDto.PrescriptionResponse;
import com.example.hospitalCrm.entity.PrescriptionEntity;
import com.example.hospitalCrm.respository.*;
import com.example.hospitalCrm.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PrescriptionItemsRepository prescriptionItemsRepository;
    private final AppointmentRespository appointmentRespository;
    private final PrescriptionRepository prescriptionRepository;


    @Override
    public PrescriptionResponse createPrescription(Long patientId, Long doctorId, Long appointmentId, PrescriptionRequest request) {

        log.info("Fetching Patient with Id: {}, Doctor with Id: {} and Appointment with Id: {}", patientId, doctorId, appointmentId);

        if(!patientRepository.existsById(patientId)) {
            throw new UsernameNotFoundException("Patient not Found with Id:"+ patientId);
        }

        if(!doctorRepository.existsById(doctorId)) {
            throw new UsernameNotFoundException("Doctor with Id: " + doctorId + " not found");
        }

        if(!appointmentRespository.existsByAppointmentIdAndPatientPatientId(appointmentId, patientId)) {
            throw new UsernameNotFoundException("Appointment");
        }

        if(prescriptionRepository.existsByPatientPatientIdAndDoctorDoctorIdAndAppointmentAppointmentId(patientId, doctorId, appointmentId)) {
            throw new RuntimeException("Prescription Item Already Exists...!");
        }



//        return null;
    }

    @Override
    public List<PrescriptionResponse> fetchAllByPatientId(Long PatientId) {
        return List.of();
    }

    @Override
    public List<PrescriptionResponse> fetchAllByDoctorId(Long docId) {
        return List.of();
    }

    @Override
    public PrescriptionResponse fetchByAppointmentId(Long appointmentId) {
        return null;
    }



    protected PrescriptionResponse mapToDtoRequest(PrescriptionEntity prescription) {
        return PrescriptionResponse.builder()
                .patientDiagnosis(prescription.getPatientDiagnosis())
                .appointmentDate(prescription.getAppointment().getAppointmentDate())
                .startTime(prescription.getAppointment().getStartTime())
                .endTime(prescription.getAppointment().getEndTime())
                .doctorName(prescription.getAppointment().getDoctor().getUser().getUserName())
                .doctorDepartment(prescription.getDoctor().getDoctorDepartment())
                .doctorSpecialization(prescription.getDoctor().getDoctorSpecialization())
                .prescriptionItems(prescription.getPrescriptionItems())

    }

    protected PrescriptionEntity mapToEntity(PrescriptionRequest request) {
        return PrescriptionEntity.builder()
                .patientDiagnosis(request.getPatientDiagnosis())
                .issuedAt(LocalDateTime.now())
                .build();
    }
}
