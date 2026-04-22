package com.example.hospitalCrm.dtos.UserDto;


import com.example.hospitalCrm.type.Role;
import jakarta.annotation.Nullable;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {


    private String userName;
    private String password;
    private String userEmail;
    private String userPhone;
}
