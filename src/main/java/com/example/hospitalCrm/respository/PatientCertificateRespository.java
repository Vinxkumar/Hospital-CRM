package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.PatientCertificatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientCertificateRespository extends JpaRepository<PatientCertificatesEntity, Long> {
}
