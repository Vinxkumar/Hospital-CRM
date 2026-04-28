package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.PrescriptionItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PrescriptionItemsRepository extends JpaRepository<PrescriptionItemsEntity, Long> {

//    PrescriptionItemsEntity findByPrescriptionId(Long  prescriptionId);

List<PrescriptionItemsEntity> findByPrescription_Patient_PatientId(Long patientId);

    List<PrescriptionItemsEntity> findByPrescription_Doctor_DoctorId(Long doctorId);

}
