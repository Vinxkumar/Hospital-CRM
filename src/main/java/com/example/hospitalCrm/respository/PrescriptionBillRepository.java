package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.PrescriptionBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionBillRepository extends JpaRepository<PrescriptionBill, Long> {
}
