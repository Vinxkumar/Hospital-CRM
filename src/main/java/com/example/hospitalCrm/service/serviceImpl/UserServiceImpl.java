package com.example.hospitalCrm.service.serviceImpl;

import com.example.hospitalCrm.utils.ModelMapperConfig;
import com.example.hospitalCrm.dtos.UserDto.UpdateUser;
import com.example.hospitalCrm.dtos.UserDto.UserLogin;
import com.example.hospitalCrm.dtos.UserDto.UserRequest;
import com.example.hospitalCrm.dtos.UserDto.UserResponse;
import com.example.hospitalCrm.entity.UsersEntity;
import com.example.hospitalCrm.respository.UserRepository;
import com.example.hospitalCrm.service.JwtUtil;
import com.example.hospitalCrm.service.UserService;
import com.example.hospitalCrm.type.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapperConfig modelMapper;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public UserResponse createNewUser(UserRequest userRequest) {

        log.info("Fetching User with UserEmail: {}", userRequest.getUserEmail());
        if(userRepository.existsByUserName(userRequest.getUserEmail())) {
            throw new IllegalArgumentException("User Already Exists Cannot Create a New User");
        }


        log.info("Creating New User with UserEmail: {}", userRequest.getUserEmail());
        UsersEntity newUser = userRepository.save(UsersEntity.builder()
                .userName(userRequest.getUserName())
                .userPassword(passwordEncoder.encode(userRequest.getPassword()))
                .userEmail(userRequest.getUserEmail())
                .userPhone(userRequest.getUserPhone())
                .role(Role.USER)
                .build());

//        if(newUser ==null) {
//            throw new RuntimeException("User Failed To Create with userEmail: "+userRequest.getUserEmail());
//        }

        log.info("Account Created Successfully with UserEmail: {}", newUser.getUserEmail());

        log.info("Attempting to Generate Token for user with UserEmail: {}", newUser.getUserEmail());

        final String token = jwtUtil.generateToken(newUser);
        if(token==null || token.isEmpty()) {
            throw new RuntimeException("Failed to Generate JWT Token for User: "+ newUser.getUsername());
        }


        return new UserResponse(token,
                newUser.getUserName(),
                newUser.getUserEmail(),
                newUser.getUserPhone(),
                newUser.getRole(),
                LocalDateTime.now());
    }


    @Transactional
    @Override
    public UserResponse updateUser(UpdateUser updateUser) {

        log.info("Fetching User with UserEmail: {}", updateUser.getUserEmail());

        UsersEntity user = userRepository.findByUserEmail(updateUser.getUserEmail());
        if(user == null ) {
            throw new UsernameNotFoundException("User Not Found with UserEmail: "+ updateUser.getUserEmail());
        }


        if(
                updateUser.getUserName() != null &&
                !updateUser.getUserName().isEmpty() &&
                !user.getUserName().equals(updateUser.getUserEmail())
        ) {

            log.info("Attempting to Update UserEmail for User with UserEmail: {}", updateUser.getUserEmail());
            user.setUserEmail(updateUser.getUserEmail());

        }

        if(
            updateUser.getUserPhone() != null &&
            !updateUser.getUserPhone().isEmpty() &&
            !updateUser.getUserPhone().equals(user.getUserEmail())
        ) {

            log.info("Attempting to Update UserPhone for User with UserEmail: {}", updateUser.getUserEmail());
            user.setUserPhone(updateUser.getUserPhone());

        }

        UsersEntity updatedUser = userRepository.save(user);

//        if(updatedUser == null) throw new RuntimeException("Unable to Update User with UserEmail: "+ updateUser.getUserEmail());

        log.info("User with UserEmail: {} updated successfully..!", updatedUser.getUsername());

//        final String token = jwtUtil.generateToken(updatedUser);

//        if(token == null) throw new RuntimeException("Unable to Generate Token for User with UserEmail: "+ updateUser.getUserEmail());

        return new UserResponse("",
                updatedUser.getUserName(),
                updatedUser.getUserEmail(),
                updatedUser.getUserPhone(),
                updatedUser.getRole(),
                LocalDateTime.now());

    }

    @Override
    public List<UserRequest> listAllUser() {
        log.info("Fetching All User Records");
        return mapToUserRequestList(userRepository.findAll());
    }

    @Transactional
    @Override
    public void deleteUserByUserEmail(String userEmail) {

        log.info("Fetching User with UserEmail: {}", userEmail);

        log.warn("Deleting User with UserName: {}", userEmail);
        userRepository.deleteByUserEmail(userEmail);

    }

    @Transactional
    @Override
    public void deleteAllUsers() {

        log.warn("Deleting all User Records");
        userRepository.deleteAll();

    }

    @Override
    public UserResponse loginUser(UserLogin userLogin) {

        log.info("Fetching User with UserName: {}", userLogin.getUserEmail());

        final UsersEntity user = userRepository.findByUserEmail(userLogin.getUserEmail());

        if(user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        log.info("Verifying Credentials for user with UserName: {}", userLogin.getUserEmail());

        if(
               !passwordEncoder.matches(userLogin.getPassword(), user.getPassword())
        ) {
            throw new RuntimeException("Invalid Credentials...!");
        }

        final String token = jwtUtil.generateToken(user);

        log.info("Generating Token for User: {}", user.getUserName());

        return new UserResponse(token,

                user.getUserName(),
                user.getUserEmail(),
                user.getUserPhone(),
                user.getRole(),
                LocalDateTime.now());

    }


//    protected UserResponse mapToUserResponseDto(UsersEntity usersEntity) {
//        return modelMapper.map();
//    }

    protected UserRequest mapToUserRequest(UsersEntity usersEntity) {
        return modelMapper.modelMapper().map(usersEntity, UserRequest.class);
    }

    protected List<UserRequest> mapToUserRequestList(List<UsersEntity> usersEntities) {
        return usersEntities.stream().map(
                usersEntity -> mapToUserRequest(usersEntity)
        ).toList();
    }

}
