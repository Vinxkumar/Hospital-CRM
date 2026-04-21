package com.example.hospitalCrm.service;

import com.example.hospitalCrm.entity.UsersEntity;
import com.example.hospitalCrm.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        UsersEntity user = userRepository.findByUserEmail(userEmail);
        if(user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        return user;
    }
}
