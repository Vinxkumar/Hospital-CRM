package com.example.hospitalCrm.service;

import com.example.hospitalCrm.dtos.UserDto.UpdateUser;
import com.example.hospitalCrm.dtos.UserDto.UserLogin;
import com.example.hospitalCrm.dtos.UserDto.UserRequest;
import com.example.hospitalCrm.dtos.UserDto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createNewUser(UserRequest userRequest);

    UserResponse updateUser(UpdateUser updateUser);

    List<UserRequest> listAllUser();

    void deleteUserByUserEmail(String userEmail);
    void deleteAllUsers();

    UserResponse loginUser(UserLogin userLogin);

}
