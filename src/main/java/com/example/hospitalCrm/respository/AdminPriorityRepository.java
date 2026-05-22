package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.AdminPriorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminPriorityRepository extends JpaRepository<AdminPriorityEntity, Long> {
    List<AdminPriorityEntity> findByAdminId(Long adminId);
}
