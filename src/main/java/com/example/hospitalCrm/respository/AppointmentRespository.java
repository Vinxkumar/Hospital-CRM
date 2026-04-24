package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRespository extends JpaRepository<AppointmentEntity, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
        FROM AppointmentEntity a
        WHERE a.doctor.doctorId = :doctorId
        AND a.appointmentDate = :date
        AND (:startTime < a.endTime AND :endTime > a.startTime)
    """)
    boolean existsOverlap(Long doctorId, LocalDate date, LocalTime startTime, LocalTime endTime);

    AppointmentEntity findByAppointmentIdAndPatientPatientId(Long appointmentId, Long patientId);

    AppointmentEntity findByAppointmentIdAndPatientPatientIdAndDoctorDoctorId(Long appointmentId, Long patientId, Long doctorId);

    List<AppointmentEntity> findByPatientPatientId(Long patientId);

    List<AppointmentEntity> findByDoctorDoctorId(Long docId);

    boolean existsByAppointmentIdAndPatientPatientId(Long appointmentId, Long patientId);

    void deleteByAppointmentIdAndPatientPatientId(Long appointmentId, Long patientId);

}
