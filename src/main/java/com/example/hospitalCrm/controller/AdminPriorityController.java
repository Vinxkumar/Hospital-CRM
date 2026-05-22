package com.example.hospitalCrm.controller;

import com.example.hospitalCrm.dtos.PriorityDto.PriorityRequest;
import com.example.hospitalCrm.dtos.PriorityDto.PriorityResponse;
import com.example.hospitalCrm.service.serviceImpl.AdminPriorityServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/priority")
@Slf4j
@RequiredArgsConstructor
public class AdminPriorityController {

    private final AdminPriorityServiceImpl adminPriorityService;


    @GetMapping("/all")
    public ResponseEntity<List<PriorityResponse>> fetchAllPriority() {
        return ResponseEntity.ok(adminPriorityService.fetchByAdminId());
    }

    @PostMapping("/new")
    public ResponseEntity<PriorityResponse> createNewPriority(@RequestBody PriorityRequest request) {
        return ResponseEntity.ok(adminPriorityService.createPriority(request));
    }

    @DeleteMapping("/{pId}")
    public ResponseEntity<String> deletePriority(@PathVariable Long pId) {
        adminPriorityService.removeById(pId);
        return ResponseEntity.ok("Delete Successfully");
    }
}
