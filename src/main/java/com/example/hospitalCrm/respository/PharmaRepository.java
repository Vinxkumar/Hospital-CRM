package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.PharmacyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmaRepository extends JpaRepository<PharmacyEntity, Long> {
}
