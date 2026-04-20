package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.DoctorAvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailabilityEntity, Long> {
}