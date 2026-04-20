package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionItemsRepository extends JpaRepository<PrescriptionEntity, Long> {
}
