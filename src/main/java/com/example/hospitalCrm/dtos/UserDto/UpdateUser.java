package com.example.hospitalCrm.dtos.UserDto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {

    private String userEmail;
    private String userPhone;

}
