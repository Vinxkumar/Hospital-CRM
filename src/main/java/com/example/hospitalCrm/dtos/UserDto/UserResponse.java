package com.example.hospitalCrm.dtos.UserDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @NotNull
    private String jwtToken;
    private LocalDateTime responseAt;

}
