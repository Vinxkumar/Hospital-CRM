package com.example.hospitalCrm.configuration;


import com.example.hospitalCrm.entity.UsersEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GetCurrentUser {

    public Long getCurrentId() {

        log.info("Filter Chain: Request to Get Current UserID.");

        log.info("Filter Chain: Getting Current User");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null) {
            log.error("Filter Chain: No current User Found");
        }
        assert auth != null;
        UsersEntity user = (UsersEntity) auth.getPrincipal();

        if(user == null) {
            log.error("Filter Chain: User Doesn't Exists");
        }

        assert user != null;
        return user.getUserId();
    }

}
