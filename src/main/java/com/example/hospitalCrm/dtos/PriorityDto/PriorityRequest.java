package com.example.hospitalCrm.dtos.PriorityDto;

import com.example.hospitalCrm.type.Priority;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriorityRequest {
    private String msg;
    private Priority priority;
}
