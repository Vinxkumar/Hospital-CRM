package com.example.hospitalCrm.dtos.UserDto;

import com.example.hospitalCrm.type.Role;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String jwtToken;
    private String userName;
    private String userEmail;
    private String userPhone;
    private Role userRole;
    private LocalDateTime responseAt;


}
