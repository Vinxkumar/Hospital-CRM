package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRespository extends JpaRepository<MedicineEntity, Long> {
}
