package com.example.hospitalCrm.service.serviceImpl;


import com.example.hospitalCrm.configuration.GetCurrentUser;
import com.example.hospitalCrm.dtos.PriorityDto.PriorityRequest;
import com.example.hospitalCrm.dtos.PriorityDto.PriorityResponse;
import com.example.hospitalCrm.entity.AdminPriorityEntity;
import com.example.hospitalCrm.entity.UsersEntity;
import com.example.hospitalCrm.respository.AdminPriorityRepository;
import com.example.hospitalCrm.respository.UserRepository;
import com.example.hospitalCrm.service.AdminPriorityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminPriorityServiceImpl implements AdminPriorityService {

    private final GetCurrentUser currentUser;
    private final UserRepository userRepository;
    private final AdminPriorityRepository adminPriorityRepository;
    private final AdminServiceImp adminService;

    @Transactional
    @Override
    public PriorityResponse createPriority(PriorityRequest priorityRequest) {
        final UsersEntity user = userRepository.findById(currentUser.getCurrentId()).orElseThrow(()-> new UsernameNotFoundException("No User Found"));
        AdminPriorityEntity newAdminPriority = adminPriorityRepository.save(AdminPriorityEntity.builder()
                .adminId(user)
                .msg(priorityRequest.getMsg())
                .priority(priorityRequest.getPriority())
                .build()
        );
        adminService.addLog(currentUser.getCurrentId(), "Added a " + priorityRequest.getPriority() + " priority");
        return mapToResponse(newAdminPriority);
    }

    @Override
    public List<PriorityResponse> fetchByAdminId() {
        final List<AdminPriorityEntity> priorityEntities = adminPriorityRepository.findByAdminId(currentUser.getCurrentId());
        log.info("Fetching all Priorities");
        return mapToResponseList(priorityEntities);
    }

    @Transactional
    @Override
    public void removeById(Long priorityId) {
        log.info("Fetching by Priority: {}", priorityId);
        if(!adminPriorityRepository.existsById(priorityId)) {
            throw new IllegalArgumentException("No Priority Found with Id: " + priorityId);
        }
        adminPriorityRepository.deleteById(priorityId);
    }



    //mapper
    protected PriorityResponse mapToResponse(AdminPriorityEntity priority) {
        return new PriorityResponse(
                priority.getId(),
                priority.getMsg(), priority.getPriority()
        );
    }

    protected List<PriorityResponse> mapToResponseList(List<AdminPriorityEntity> adminPriorityEntities) {
        return adminPriorityEntities.stream().map(
                this::mapToResponse
        ).toList();
    }


}
