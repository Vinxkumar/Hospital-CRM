package com.example.hospitalCrm.service;

import com.example.hospitalCrm.dtos.PriorityDto.PriorityRequest;
import com.example.hospitalCrm.dtos.PriorityDto.PriorityResponse;

import java.util.List;

public interface AdminPriorityService {
    PriorityResponse createPriority(PriorityRequest priorityRequest);
    List<PriorityResponse> fetchByAdminId();
    void removeById(Long priorityId);
}
