package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<MedicineEntity, Long> {

     MedicineEntity findByMedicineFullName(String name);

    MedicineEntity findByMedicineGenericName(String name);

    Boolean existsByMedicineFullName(String name);
}
