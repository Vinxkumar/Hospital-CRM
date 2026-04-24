package com.example.hospitalCrm.service.serviceImpl;

import com.example.hospitalCrm.dtos.PatientDto.PatientResponse;
import com.example.hospitalCrm.dtos.PatientDto.PatientUpdateRequest;
import com.example.hospitalCrm.entity.PatientEntity;
import com.example.hospitalCrm.respository.PatientRepository;
import com.example.hospitalCrm.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Transactional
    @Override
    public PatientResponse updatePatient(Long patientId, PatientUpdateRequest patientUpdateRequest) {

        log.info("Fetching Patient with Id: {}", patientId);

        PatientEntity patient = patientRepository.findById(patientId).orElseThrow(()-> new UsernameNotFoundException("Patient with Id: " + patientId + " Not Found"));
        if(
                patientUpdateRequest.getPatientPhone() != null &&
                !patientUpdateRequest.getPatientPhone().isEmpty() &&
                !patient.getUser().getUserPhone().equals(patientUpdateRequest.getPatientPhone())
        ) {
            patient.getUser().setUserPhone(patientUpdateRequest.getPatientPhone());
        }

        if(
            patientUpdateRequest.getEmergencyPhone() !=null &&
            !patientUpdateRequest.getEmergencyPhone().isEmpty() &&
            !patient.getEmergencyPhone().equals(patientUpdateRequest.getEmergencyPhone())
        ) {
            patient.setEmergencyPhone(patientUpdateRequest.getEmergencyPhone());
        }

        if (
            patientUpdateRequest.getPatientAddress() !=null &&
            !patientUpdateRequest.getPatientAddress().isEmpty() &&
            !patient.getPatientAddress().equals(patientUpdateRequest.getPatientAddress())
        ) {
            patient.setPatientAddress(patientUpdateRequest.getPatientAddress());
        }

        log.info("Updating Patient with Id: {}", patientId);
        final PatientEntity updatedPatient = patientRepository.save(patient);

        return new PatientResponse(
                "",
                updatedPatient.getUser().getUserName(),
                updatedPatient.getUser().getUserEmail(),
                updatedPatient.getUser().getUserPhone(),
                updatedPatient.getEmergencyPhone(),
                updatedPatient.getPatientBloodGroup(),
                updatedPatient.getPatientAddress(),
                //TODO: Have to change to respective responses
                List.of(),  //appointments
                List.of(),  // prescription
                List.of()  // certificates

        );
    }
}
