package com.example.hospitalCrm.dtos.UserDto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {

    private String  userName;
    private String password;

}
