package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.PrescriptionEntity;
import com.example.hospitalCrm.entity.PrescriptionItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionItemsRepository extends JpaRepository<PrescriptionEntity, Long> {

    List<PrescriptionItemsEntity> findByPrescriptionId(Long  prescriptionId);

}
