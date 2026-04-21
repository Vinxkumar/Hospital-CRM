package com.example.hospitalCrm.controller;


import com.example.hospitalCrm.dtos.UserDto.UserLogin;
import com.example.hospitalCrm.dtos.UserDto.UserRequest;
import com.example.hospitalCrm.dtos.UserDto.UserResponse;
import com.example.hospitalCrm.service.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserLogin userLogin) {

        log.info("REST: request to login by User: {}", userLogin.getUserEmail());

        return ResponseEntity.ok(userService.loginUser(userLogin));
    }

    @PostMapping("/signup")
    private ResponseEntity<UserResponse> signup(@RequestBody UserRequest userRequest) {

        log.info("REST: request to signup");

        return ResponseEntity.ok(userService.createNewUser(userRequest));
    }

}
