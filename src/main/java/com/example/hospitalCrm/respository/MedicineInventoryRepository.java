package com.example.hospitalCrm.respository;


import com.example.hospitalCrm.entity.MedicineInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineInventoryRepository extends JpaRepository<MedicineInventory, Long> {
}
