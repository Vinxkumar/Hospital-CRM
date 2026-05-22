package com.example.hospitalCrm.controller;

import com.example.hospitalCrm.configuration.GetCurrentUser;
import com.example.hospitalCrm.dtos.MedicineDto.AddMedicineRequest;
import com.example.hospitalCrm.dtos.MedicineDto.MedicineResponse;
import com.example.hospitalCrm.dtos.MedicineInventoryDto.UpdateInventoryRequest;
import com.example.hospitalCrm.service.serviceImpl.AdminServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/admin/medicine")
@RequiredArgsConstructor
public class MedicineController {
    private final AdminServiceImp adminService;
    private final GetCurrentUser getCurrentUser;

    @PostMapping("/new")
    public ResponseEntity<MedicineResponse> addNewMedicine(@RequestBody AddMedicineRequest addMedicineRequest) {
        return ResponseEntity.ok(adminService.addMedicine(getCurrentUser.getCurrentId(), addMedicineRequest));
    }

    @GetMapping("/{mId}")
    public ResponseEntity<MedicineResponse> fetchById(@PathVariable Long mId) {
        return ResponseEntity.ok(adminService.fetchByMedicineId(getCurrentUser.getCurrentId(), mId));
    }

    @PutMapping("/{mId}")
    public ResponseEntity<MedicineResponse> updateById(@PathVariable Long mId, @RequestBody UpdateInventoryRequest request) {
        return ResponseEntity.ok(adminService.updateMedicine(getCurrentUser.getCurrentId(), mId, request));
    }

    @DeleteMapping("/{mId}")
    public ResponseEntity<String> deleteById(@PathVariable Long mId) {
        adminService.removeMedicine(getCurrentUser.getCurrentId(), mId);
        return ResponseEntity.ok("Medicine Deleted with Id"+ mId+"Successfully");
    }
}
