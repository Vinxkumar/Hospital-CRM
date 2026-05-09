package com.example.hospitalCrm.dtos.AdminLogDto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogResponse {

    private LocalDateTime timeStamp;

    private String log;

}
