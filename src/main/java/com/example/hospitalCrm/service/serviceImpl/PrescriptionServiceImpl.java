package com.example.hospitalCrm.service.serviceImpl;

import com.example.hospitalCrm.dtos.PrescriptionDto.PrescriptionRequest;
import com.example.hospitalCrm.dtos.PrescriptionDto.PrescriptionResponse;
import com.example.hospitalCrm.dtos.PrescriptionItemsDto.PrescriptionItemsRequest;
import com.example.hospitalCrm.dtos.PrescriptionItemsDto.PrescriptionItemsResponse;
import com.example.hospitalCrm.entity.*;
import com.example.hospitalCrm.respository.*;
import com.example.hospitalCrm.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final MedicineRespository medicineRespository;


    @Override
    public PrescriptionResponse createPrescription(Long patientId, Long doctorId, Long appointmentId, PrescriptionRequest request, List<PrescriptionItemsRequest> prescriptionItemsRequests) {

        log.info("Fetching Patient with Id: {}, Doctor with Id: {} and Appointment with Id: {}", patientId, doctorId, appointmentId);

        if(!appointmentRespository.existsByAppointmentIdAndPatientPatientIdAndDoctorDoctorId(appointmentId, patientId, doctorId)) {
            throw new IllegalArgumentException("Appointment doesn't Belong to the Patient or the Appointment doesn't Belong to the Doctor");
        }

        PatientEntity patient = patientRepository.findById(patientId).orElseThrow(()-> new UsernameNotFoundException("Patient Not Found with Id: " + patientId));
        DoctorEntity doctor = doctorRepository.findById(doctorId).orElseThrow(()-> new UsernameNotFoundException("Doctor Not Found with Id: " + doctorId));
        AppointmentEntity appointment = appointmentRespository.findById(appointmentId).orElseThrow(()-> new UsernameNotFoundException("Appointment Not Found with Id: " + appointmentId));



//        if(!patientRepository.existsById(patientId)) {
//            throw new UsernameNotFoundException("Patient not Found with Id:"+ patientId);
//        }
//
//        if(!doctorRepository.existsById(doctorId)) {
//            throw new UsernameNotFoundException("Doctor with Id: " + doctorId + " not found");
//        }
//
//        if(!appointmentRespository.existsByAppointmentIdAndPatientPatientId(appointmentId, patientId)) {
//            throw new UsernameNotFoundException("Appointment");
//        }
//
//        if(prescriptionRepository.existsByPatientPatientIdAndDoctorDoctorIdAndAppointmentAppointmentId(patientId, doctorId, appointmentId)) {
//            throw new RuntimeException("Prescription Already Exists...!");
//        }g

        PrescriptionEntity newPrescription = mapToEntity(request);
        newPrescription.setAppointment(appointment);
        newPrescription.setDoctor(doctor);
        newPrescription.setPatient(patient);

        List<PrescriptionItemsEntity> prescriptionItemsEntities = new ArrayList<>();

        for(PrescriptionItemsRequest prescriptionItemRequest : prescriptionItemsRequests) {
            PrescriptionItemsEntity prescriptionItem = PrescriptionItemsEntity.builder()
                    .prescription(newPrescription)
                    .prescriptionFrequency(prescriptionItemRequest.getPrescriptionFrequency())
                    .medicine(medicineRespository.findById(prescriptionItemRequest.getMedicineId()).orElseThrow(
                            () -> new UsernameNotFoundException("Medicine With This Id not not Found")
                    ))
                    .prescriptionDurationInDays(prescriptionItemRequest.getPrescriptionDurationInDays())
                    .doctorNote(prescriptionItemRequest.getDoctorNote())
                    .medicineDosage(prescriptionItemRequest.getMedicineDosage())
                    .build();

            PrescriptionItemsEntity prescriptionItems = prescriptionItemsRepository.save(prescriptionItem);

            prescriptionItemsEntities.add(prescriptionItems);
        }

        log.info("Adding Prescription Items for Prescription with Appointment Id: {}", appointmentId);

        newPrescription.setPrescriptionItems(prescriptionItemsEntities);
        newPrescription.setEditable(false);

        PrescriptionEntity savedPrescription = prescriptionRepository.save(newPrescription);

        log.info("Saved Prescription of Appointment with Id: {}", appointmentId);

        return mapToDtoResponse(savedPrescription);
    }

    @Override
    public List<PrescriptionResponse> fetchAllByPatientId(Long patientId) {

        log.info("Fetching Patient with Id; {}", patientId);

        if(!patientRepository.existsById(patientId)) {
                throw new UsernameNotFoundException("Patient with Id" + patientId + " Not Found");
        }

        log.info("Fetching all Prescriptions of Patient with Id: {}", patientId);

        List<PrescriptionEntity> prescriptions = prescriptionRepository.findByPatient_PatientId(patientId);

        if(prescriptions == null) {
            throw new IllegalArgumentException("No Appointment Found");
        }

        return mapToResponseList(prescriptions);
    }

    @Override
    public List<PrescriptionResponse> fetchAllByDoctorId(Long docId) {

        log.info("Fetching Doctor with Id; {}", docId);

        if(!doctorRepository.existsById(docId)) {
            throw new UsernameNotFoundException("Doctor Not Found with Id: "+ docId);
        }

        log.info("Fetching all the Prescription Issued by Doctor with Id: {}", docId);

        List<PrescriptionEntity> prescriptionEntities = prescriptionRepository.findByDoctor_DoctorId(docId);

        if(prescriptionEntities == null) {
            throw new RuntimeException("No Prescriptions Found for Doctor with Id: "+docId);
        }

        return mapToResponseList(prescriptionEntities);
    }

    @Override
    public PrescriptionResponse fetchByAppointmentId(Long appointmentId) {

        log.info("Fetching Appointment with Id; {}", appointmentId);

        if(!appointmentRespository.existsById(appointmentId)) {
            throw new UsernameNotFoundException("Appointment not Found with Id: "+ appointmentId);
        }

        log.info("Fetching Prescription for Appointment with Id: {}", appointmentId);

        PrescriptionEntity prescription = prescriptionRepository.findByAppointment_AppointmentId(appointmentId);

        return mapToDtoResponse(prescription);
    }



    protected PrescriptionResponse mapToDtoResponse(PrescriptionEntity prescription) {
        return PrescriptionResponse.builder()
                .patientDiagnosis(prescription.getPatientDiagnosis())
                .appointmentDate(prescription.getAppointment().getAppointmentDate())
                .startTime(prescription.getAppointment().getStartTime())
                .endTime(prescription.getAppointment().getEndTime())
                .doctorName(prescription.getAppointment().getDoctor().getUser().getUserName())
                .doctorDepartment(prescription.getDoctor().getDoctorDepartment())
                .doctorSpecialization(prescription.getDoctor().getDoctorSpecialization())
                .prescriptionItems(mapToPrescriptionItemsResponseList(prescription.getPrescriptionItems()))
                .build();

    }

    protected List<PrescriptionResponse> mapToResponseList(List<PrescriptionEntity> prescriptionEntities) {
        return prescriptionEntities.stream().map(
                prescription -> mapToDtoResponse(prescription)
        ).toList();
    }

    protected PrescriptionEntity mapToEntity(PrescriptionRequest request) {
        return PrescriptionEntity.builder()
                .patientDiagnosis(request.getPatientDiagnosis())
                .issuedAt(LocalDateTime.now())
                .build();
    }



    protected PrescriptionItemsResponse mapToPrescriptionItemsResponse(PrescriptionItemsEntity prescriptionItem) {
        return PrescriptionItemsResponse.builder()
                .medicineName(prescriptionItem.getMedicine().getMedicineFullName())
                .medicineDosage(prescriptionItem.getMedicineDosage())
                .prescriptionFrequency(prescriptionItem.getPrescriptionFrequency())
                .prescriptionDurationInDays(prescriptionItem.getPrescriptionDurationInDays())
                .doctorNote(prescriptionItem.getDoctorNote())
                .build();
    }

    protected List<PrescriptionItemsResponse> mapToPrescriptionItemsResponseList(List<PrescriptionItemsEntity> prescriptionItems) {
        return prescriptionItems.stream().map(
                prescriptionItem -> mapToPrescriptionItemsResponse(prescriptionItem)
        ).toList();
    }
}
