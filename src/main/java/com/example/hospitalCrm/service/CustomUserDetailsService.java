package com.example.hospitalCrm.service;

import com.example.hospitalCrm.entity.UsersEntity;
import com.example.hospitalCrm.respository.UserRepository;
import com.nimbusds.jose.crypto.opts.UserAuthenticationRequired;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity user = userRepository.findByUserName(username);
        if(user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        return new User(
                user.getUsername(), user.getUserPassword(), List.of(new SimpleGrantedAuthority("ROLE_PATIENT"))
        );
    }
}
