package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.AdminLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AdminLogRepository extends JpaRepository<AdminLogEntity, Long> {

    List<AdminLogEntity> findByAdminId(long adminId);
}