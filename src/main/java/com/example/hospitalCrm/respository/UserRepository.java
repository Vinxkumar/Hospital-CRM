package com.example.hospitalCrm.respository;

import com.example.hospitalCrm.dtos.UserDto.UserProject;
import com.example.hospitalCrm.dtos.UserDto.UserResponse;
import com.example.hospitalCrm.entity.UsersEntity;
import com.example.hospitalCrm.type.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {

    UsersEntity findByUserName(String userName);
    UsersEntity findByUserEmail(String userEmail);
    UsersEntity findByUserPhone(String userPhone);

    List<UsersEntity> findByRole(Role role);

    boolean existsByUserName(String userName);
    boolean existsByUserEmail(String userEmail);
    boolean existsByUserPhone(String userPhone);
    void deleteByUserName(String UserName);
    void deleteByUserEmail(String userEmail);
    void deleteByUserPhone(String userPhone);


    @Query("select u.userName as userName, u.userEmail as userEmail, u.userPhone as userPhone from UsersEntity u where u.id= :id and  u.role = :role ")
    UserProject findByUserRole(@Param("id") Long id, @Param("role") Role role);


}
