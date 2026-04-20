package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRespository extends JpaRepository<AppointmentEntity, Long> {
}
