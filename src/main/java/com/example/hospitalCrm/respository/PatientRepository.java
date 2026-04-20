package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
}
