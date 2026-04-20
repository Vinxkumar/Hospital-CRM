package com.example.hospitalCrm.configuration;

import com.example.hospitalCrm.entity.UsersEntity;
import com.example.hospitalCrm.respository.UserRepository;
import com.example.hospitalCrm.service.JwtTokenBuilder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenBuilder jwtTokenBuilder;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authToken = request.getHeader("Authorization");

        if(authToken == null || !authToken.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authToken.substring(7);
        final String userName = jwtTokenBuilder.getUserName(token);

        if(userName !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsersEntity user = userRepository.findByUserName(userName);
            if(user == null) {
                throw new UsernameNotFoundException("User Name Not Found");
            }

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword(), null
            );
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(request, response);
        }

    }
}
