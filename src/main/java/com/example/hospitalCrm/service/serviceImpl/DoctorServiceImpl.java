package com.example.hospitalCrm.service.serviceImpl;

import com.example.hospitalCrm.dtos.DoctorDto.DoctorAppointmentViewResponse;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorResponse;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorUpdateRequest;
import com.example.hospitalCrm.entity.AppointmentEntity;
import com.example.hospitalCrm.entity.DoctorEntity;
import com.example.hospitalCrm.respository.AppointmentRespository;
import com.example.hospitalCrm.respository.DoctorRepository;
import com.example.hospitalCrm.service.DoctorService;
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
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final AppointmentRespository appointmentRespository;

    @Transactional
    @Override
    public DoctorResponse setAvailability(Long docId, Boolean availability) {

        log.info("Fetching Doctor with Id: {}", docId);

        DoctorEntity doctor = doctorRepository.findById(docId).orElseThrow(() -> new UsernameNotFoundException("Doctor with Id: "+ docId + " Not Found "));

        doctor.setDoctorAvailable(availability);

        log.info("Updating Availability with Id; {}", docId);

        final DoctorEntity savedDoc  = doctorRepository.save(doctor);

        return new DoctorResponse(
                "",
                savedDoc.getUser().getUserName(),
                savedDoc.getUser().getUserPhone(),
                savedDoc.getDoctorAlternativePhone(),
                savedDoc.getDoctorSpecialization(),
                savedDoc.getDoctorQualification(),
                savedDoc.getDoctorLicenceNo(),
                savedDoc.getDoctorAvailable()
        );
    }

    @Override
    public List<DoctorAppointmentViewResponse> listAllAppointments(Long docId) {

        log.info("Fetching Doctor with Id: {}", docId);

        if(!doctorRepository.existsByDoctorId(docId)) {
            throw new UsernameNotFoundException("Doctor with Id: " + docId + " Not Found");
        }

        log.info("Fetching Appointment for Doctor with Id: {}", docId);
        List<AppointmentEntity> appointments = appointmentRespository.findByDoctorDoctorId(docId);

        if(appointments == null) {
            throw new RuntimeException("No Appointments Found for Doctor with Id: " + docId);
        }

        return mapToDtoList(appointments);
    }

    @Transactional
    @Override
    public DoctorAppointmentViewResponse updateAppointmentStatus(Long docId, Long appointmentId, AppointmentStatus appointmentStatus) {

        log.info("Fetching Doctor with Id: {}", docId);

        if(!doctorRepository.existsByDoctorId(docId)) {
            throw new UsernameNotFoundException("Doctor with Id: " + docId + " Not Found");
        }

        log.info("Fetching Appointment with Id: {} for Doctor with Id: {}", appointmentId, docId);

        AppointmentEntity appointment = appointmentRespository.findById(appointmentId).orElseThrow(()-> new IllegalArgumentException("Appointment with Id " + appointmentId + "Not  Found For Doctor with Id: "+  docId));

        appointment.setAppointmentStatus(appointmentStatus);

        log.info("Updating Appointment Status");

        AppointmentEntity savedAppointment = appointmentRespository.save(appointment);

//        if(savedAppointment == null)
        return mapToDto(savedAppointment);
    }

    @Transactional
    @Override
    public DoctorResponse updateDoctor(Long docId, DoctorUpdateRequest doctorUpdateRequest) {

        log.info("Fetching Doctor with Id: {}", docId);
        DoctorEntity doctor = doctorRepository.findById(docId).orElseThrow(()-> new UsernameNotFoundException("Doctor Not Found with Id: " + docId));

        if(
            doctorUpdateRequest.getPhone() !=null &&
            !doctorUpdateRequest.getPhone().isEmpty() &&
            !doctor.getUser().getUserPhone().equals(doctorUpdateRequest.getPhone())
        ) {
            doctor.getUser().setUserPhone(doctorUpdateRequest.getPhone());
        }

        if(
            doctorUpdateRequest.getEmergencyPhone() !=null &&
            !doctorUpdateRequest.getEmergencyPhone().isEmpty() &&
            !doctor.getDoctorAlternativePhone().equals(doctorUpdateRequest.getEmergencyPhone())
        ) {
            doctor.setDoctorAlternativePhone(doctorUpdateRequest.getEmergencyPhone());
        }

        if(
            doctorUpdateRequest.getDepartment() !=null &&
            !doctorUpdateRequest.getDepartment().equals(doctor.getDoctorDepartment())
        ) {
            doctor.setDoctorDepartment(doctorUpdateRequest.getDepartment());
        }

        if(
            doctorUpdateRequest.getSpecialization() !=null &&
            !doctorUpdateRequest.getSpecialization().isEmpty() &&
            !doctorUpdateRequest.getSpecialization().equals(doctor.getDoctorSpecialization())
        ) {
            doctor.setDoctorSpecialization(doctorUpdateRequest.getSpecialization());
        }

        log.info("Updating Doctor with Id: {}", docId);

        final DoctorEntity savedDoc = doctorRepository.save(doctor);

        return new DoctorResponse(
                "",
                savedDoc.getUser().getUserName(),
                savedDoc.getUser().getUserPhone(),
                savedDoc.getDoctorAlternativePhone(),
                savedDoc.getDoctorSpecialization(),
                savedDoc.getDoctorQualification(),
                savedDoc.getDoctorLicenceNo(),
                savedDoc.getDoctorAvailable()
        );

    }




    // Mappers
    protected DoctorAppointmentViewResponse mapToDto(AppointmentEntity appointment) {
        return new DoctorAppointmentViewResponse(
                appointment.getPatient().getUser().getUserName(),
                appointment.getMessage(),
                appointment.getAppointmentDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getAppointmentStatus()
        );
    }

    protected List<DoctorAppointmentViewResponse> mapToDtoList(List<AppointmentEntity> appointmentEntities) {
        return appointmentEntities.stream().map(
                appointment -> mapToDto(appointment)
        ).toList();
    }
}
