package com.example.hospitalCrm.dtos.UserDto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {

    @NotNull
    private String userEmail;

    private String userName;
    private String userPhone;

}
