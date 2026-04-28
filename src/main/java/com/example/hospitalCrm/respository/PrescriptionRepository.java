package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long> {

    Boolean existsByPatientPatientIdAndDoctorDoctorIdAndAppointmentAppointmentId(Long patientId, Long docId, Long appointmentId);
    PrescriptionEntity findByPatientPatientIdAndDoctorDoctorIdAndAppointmentAppointmentId(Long patientId, Long docId, Long appointmentId);
    List<PrescriptionEntity> findByPatient_PatientId(Long patientId);
    List<PrescriptionEntity> findByDoctor_DoctorId(Long doctorId);
    PrescriptionEntity findByAppointment_AppointmentId(Long appointmentId);
}
