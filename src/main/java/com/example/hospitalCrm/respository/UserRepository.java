package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UsersEntity, Long> {

    UsersEntity findByUserName(String userName);
    UsersEntity findbyUserEmail(String userEmail);
    UsersEntity findByUserPhone(String userPhone);

}
