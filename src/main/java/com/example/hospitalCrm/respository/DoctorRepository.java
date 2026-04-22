package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

//    DoctorEntity findByDoctorId(String userEmail);

    DoctorEntity findByDoctorLicenceNo(String doctorLicenceNo);

    Boolean existsByDoctorId(Long id);

    Boolean existsByDoctorLicenceNo(String doctorLicenceNo);

    void deleteByDoctorLicenceNo(String doctorLicenceNo);
    void deleteByDoctorId(Long id);
}
