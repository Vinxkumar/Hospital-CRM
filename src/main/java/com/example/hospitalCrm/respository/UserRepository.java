package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {

    UsersEntity findByUserName(String userName);
    UsersEntity findByUserEmail(String userEmail);
    UsersEntity findByUserPhone(String userPhone);

    List<UsersEntity> findByRole(String role);

    boolean existsByUserName(String userName);
    boolean existsByUserEmail(String userEmail);
    boolean existsByUserPhone(String userPhone);
    void deleteByUserName(String UserName);
    boolean deleteByUserEmail(String userEmail);
    boolean deleteByUserPhone(String userPhone);


}
