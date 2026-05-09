package com.example.hospitalCrm.service.serviceImpl;

import com.example.hospitalCrm.dtos.AdminLogDto.LogResponse;
import com.example.hospitalCrm.dtos.KeyMetricsResponse;
import com.example.hospitalCrm.dtos.MedicineDto.AddMedicineRequest;

import com.example.hospitalCrm.dtos.MedicineDto.MedicineResponse;

import com.example.hospitalCrm.dtos.MedicineInventoryDto.InventoryResponse;
import com.example.hospitalCrm.dtos.MedicineInventoryDto.UpdateInventoryRequest;
import com.example.hospitalCrm.entity.*;
import com.example.hospitalCrm.respository.*;
import com.example.hospitalCrm.utils.ModelMapperConfig;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorRequest;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorResponse;
import com.example.hospitalCrm.dtos.PatientDto.PatientRequest;
import com.example.hospitalCrm.dtos.PatientDto.PatientResponse;
import com.example.hospitalCrm.dtos.UserDto.UserProject;
import com.example.hospitalCrm.dtos.UserDto.UserRequest;
import com.example.hospitalCrm.dtos.UserDto.UserResponse;
import com.example.hospitalCrm.service.AdminService;
import com.example.hospitalCrm.service.JwtUtil;
import com.example.hospitalCrm.type.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ModelMapperConfig mapper;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PharmaRepository pharmaRepository;
    private final MedicineRepository medicineRepository;
    private final MedicineInventoryRepository medicineInventoryRepository;
    private final AdminLogRepository adminLogRepository;

    // Creating

    @Transactional
    @Override
    public UserResponse createNewAdminUser(UserRequest userRequest) {

        log.info("Fetching User with UserEmail: {}", userRequest.getUserEmail());

        if(userRepository.existsByUserEmail(userRequest.getUserEmail())) {
            throw new RuntimeException("User Already Registered with this Email: " + userRequest.getUserEmail());
        }

        log.info("Creating New Admin Account");

        UsersEntity newAdmin = userRepository.save(UsersEntity.builder()
                .userName(userRequest.getUserName())
                .userEmail(userRequest.getUserEmail())
                .userPassword(passwordEncoder.encode(userRequest.getPassword()))
                .role(Role.ADMIN)
                .userPhone(userRequest.getUserPhone())
                .build());

        final String token = jwtUtil.generateToken(newAdmin);


        addLog(newAdmin.getUserId(), "Added New Admin");

        return new UserResponse(
                token,
                newAdmin.getUserName(),
                newAdmin.getUserEmail(),
                newAdmin.getUserPhone(),
                newAdmin.getRole(),
                LocalDateTime.now()
        );

    }

    @Transactional
    @Override
    public DoctorResponse createNewDoctor(Long adminId, DoctorRequest doctorRequest) {

        log.info("Fetching User with UserEmail: {}", doctorRequest.getUser().getUserEmail());

        if(userRepository.existsByUserEmail(doctorRequest.getUser().getUserEmail())) {
            throw new RuntimeException("User Already Registered with this Email: " + doctorRequest.getUser().getUserEmail());
        }

        log.info("Creating New User Account For UserEmail: {} ", doctorRequest.getUser().getUserEmail());

        UsersEntity newUser = userRepository.save(UsersEntity.builder()
                .userName(doctorRequest.getUser().getUserName())
                .userEmail(doctorRequest.getUser().getUserEmail())
                .userPassword(passwordEncoder.encode(doctorRequest.getUser().getPassword()))
                .role(Role.DOCTOR)
                .userPhone(doctorRequest.getUser().getUserPhone())
                .build());


        log.info("Creating New Doctor Account for User with UserEmail: {}", doctorRequest.getUser().getUserEmail());

        DoctorEntity newDoctor = doctorRepository.save(
                DoctorEntity.builder()
                        .user(newUser)
                        .doctorAlternativePhone(doctorRequest.getDoctorAlternativePhone())
                        .doctorSpecialization(doctorRequest.getDoctorSpecialization())
                        .doctorQualification(doctorRequest.getDoctorQualification())
                        .doctorLicenceNo(doctorRequest.getDoctorLicenceNo())
                        .doctorAvailable(doctorRequest.getDoctorAvailable())
                        .doctorDepartment(doctorRequest.getDoctorDepartment())
                        .build()
        );

        log.info("Doctor Account Create Successfully with Email: {}", newDoctor.getUser().getUserEmail());

        addLog(adminId, "Added New Doctor '" + newDoctor.getUser().getUserName() + "' in Department " + newDoctor.getDoctorDepartment());

        return new DoctorResponse(
                newDoctor.getUser().getUserName(),
                newDoctor.getUser().getUserPhone(),
                newDoctor.getDoctorAlternativePhone(),
                newDoctor.getDoctorSpecialization(),
                newDoctor.getDoctorQualification(),
                newDoctor.getDoctorLicenceNo(),
                newDoctor.getDoctorAvailable()
        );

    }



    @Override
    public UserResponse createNewPharma(Long adminId,UserRequest userRequest) {
        if(userRepository.existsByUserEmail(userRequest.getUserEmail())) {
            throw new RuntimeException("User Already Registered with this Email: " + userRequest.getUserEmail());
        }

        UsersEntity newPharma = userRepository.save(UsersEntity.builder()
                .userName(userRequest.getUserName())
                .userEmail(userRequest.getUserEmail())
                .userPassword(passwordEncoder.encode(userRequest.getPassword()))
                .role(Role.PHARMA_STAFF)
                .userPhone(userRequest.getUserPhone())
                .build());

        addLog(adminId, "Created New Parma Staff ");

        return new UserResponse(
                "",
                newPharma.getUserName(),
                newPharma.getUserEmail(),
                newPharma.getUserPhone(),
                newPharma.getRole(),
                LocalDateTime.now());
    }

    @Transactional
    @Override
    public PatientResponse createNewPatient(Long adminId, PatientRequest patientRequest) {

        log.info("Fetching User with UserEmail: {}",patientRequest.getUser().getUserEmail());

        if(userRepository.existsByUserEmail(patientRequest.getUser().getUserEmail())) {
            throw new RuntimeException("User Already Registered with this Email: " + patientRequest.getUser().getUserEmail());
        }

        UsersEntity newPatient = UsersEntity.builder()
                .userName(patientRequest.getUser().getUserName())
                .userEmail(patientRequest.getUser().getUserEmail())
                .userPassword(passwordEncoder.encode(patientRequest.getUser().getPassword()))
                .role(Role.PATIENT)
                .userPhone(patientRequest.getUser().getUserPhone())
                .build();

        if(newPatient == null) {
            throw new RuntimeException("Unable to Create a New User Account for User: " + patientRequest.getUser().getUserEmail());
        }

        log.info("Attempt to Create Patient Account for User: {}", patientRequest.getUser().getUserEmail());


        PatientEntity newPatientEntity = patientRepository.save(
                PatientEntity.builder()
                        .user(newPatient)
                        .emergencyPhone(patientRequest.getEmergencyPhone())
                        .patientAddress(patientRequest.getPatientAddress())
                        .patientBloodGroup(patientRequest.getPatientBloodGroup())
                        .build()
        );

//        if(newPatientEntity == null) {
//            throw new RuntimeException("Failed to Create Patient Account for User:"+ patientRequest.getUser().getUserEmail());
//        }


        addLog(adminId, "Admitted New Patient with Id: " +newPatientEntity.getPatientId());

        log.info("Patient Account Created Successfully....!");

        return new PatientResponse(
                newPatient.getUserName(),
                newPatient.getUserEmail(),
                newPatient.getUserPhone(),
                newPatientEntity.getEmergencyPhone(),
                newPatientEntity.getPatientBloodGroup(),
                newPatientEntity.getPatientAddress(),
                //TODO: Have to change to respective responses
                List.of(),  //appointments
                List.of(),  // prescription
                List.of()  // certificates

        );
//        return new UserResponse(token, LocalDateTime.now());
    }

    @Transactional
    @Override
    public MedicineResponse addMedicine(Long adminId, AddMedicineRequest medicineRequest) {

        log.info("Fetching Admin with Id: {}", adminId);

        if(!userRepository.existsById(adminId)) {
            throw new UsernameNotFoundException("Admin Not Found with Id: "+ adminId);
        }

        log.info("Fetching Medicine with Name: {}", medicineRequest.getMedicineFullName());

        if(medicineRepository.existsByMedicineFullName(medicineRequest.getMedicineFullName())) {
            throw new IllegalArgumentException("Medicine Already Exists");
        }

        MedicineEntity newMedicine = mapToMedicineEntity(medicineRequest);

        MedicineInventory inventory = mapToInventoryEntity(medicineRequest);


        inventory.setMedicine(newMedicine);
        newMedicine.setMedicineInventories(inventory);

        log.info("Attempt to Add Medicine with name: {}", medicineRequest.getMedicineFullName());

        final MedicineEntity savedMedicine = medicineRepository.save(newMedicine);
        final MedicineInventory savedInventory = medicineInventoryRepository.save(inventory);


        addLog(adminId, "Added New Medicine with Id: " + newMedicine.getMedicineId());

//        if(savedInventory == null || savedMedicine == null)
        return mapToMedicineResponse(savedMedicine);
    }

    // Listing

    @Override
    public UserResponse fetchByAdminUserId(Long id) {
        log.info("Fetching Admin User By Id");
        UserProject userProject =  userRepository.findByUserRole(id, Role.ADMIN);

        addLog(id, "Fetched Admin with Id: " + id);

        return new UserResponse(
                "",
                userProject.getUserName(),
                userProject.getUserEmail(),
                userProject.getUserPhone(),
                userProject.getRole(),
                LocalDateTime.now()
        );
    }

    @Override
    public DoctorResponse fetchByDoctorId(Long id) {
        log.info("Fetching Doctor by Id: {}", id);

        return mapToDoctorResponse(doctorRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User Not Found")));
    }

    @Override
    public UserResponse fetchByPharmaId(Long id) {
        log.info("Fetching Pharma with Id: {}", id);
        UserProject userProject =  userRepository.findByUserRole(id, Role.PHARMA_STAFF);
        return new UserResponse(
                "",
                userProject.getUserName(),
                userProject.getUserEmail(),
                userProject.getUserPhone(),
                userProject.getRole(),
                LocalDateTime.now()
        );
    }

    @Override
    public PatientResponse fetchByPatientId(Long id) {
        log.info("Fetching Patient by Id: {}", id);
        return mapToPatientResponse(patientRepository.findById(id).orElseThrow(()-> new RuntimeException("User Not Found")));
    }

    @Override
    public MedicineResponse fetchByMedicineId(Long adminId, Long medicineId) {
        log.info("Fetching Admin with Id: {}", adminId);

        if(!userRepository.existsById(adminId)) {
            throw new UsernameNotFoundException("Admin not Found with Id: "+ adminId);
        }

        final MedicineEntity medicine = medicineRepository.findById(medicineId).orElseThrow(() -> new IllegalArgumentException("Medicine Not Found by Id: "+ medicineId));

        addLog(adminId, "Fetched Medicine with Id: "+ medicineId);

        return mapToMedicineResponse(medicine);
    }


    @Override
    public List<MedicineResponse> fetchAllMedicine(Long adminId) {

        log.info("Fetching Admin with Id: {}", adminId);

        if(!userRepository.existsById(adminId)) {
            throw new UsernameNotFoundException("Admin not Found with Id: "+ adminId);
        }

        log.info("Fetching All Medicines");

        addLog(adminId, "Fetched All Medicine");

        return mapToMedicineResponseList(medicineRepository.findAll());
    }

    @Override
    public List<UserRequest> listAllAdmin() {

        log.info("Fetching all the Admin Users");

        return mapToUserRequestList(userRepository.findByRole(Role.ADMIN));
    }

    @Override
    public List<DoctorResponse> listAllDoctor() {

        log.info("Fetching all the Doctors");

//        return mapToUserRequestList(userRepository.findByRole(Role.DOCTOR));
        return mapToDoctorResponseList(doctorRepository.findAll());
    }

    @Override
    public List<UserRequest> listAllPharma() {

        log.info("Fetching all the Admin Users");

        return mapToUserRequestList(userRepository.findByRole(Role.PHARMA_STAFF));
    }

    @Override
    public List<PatientResponse> listAllPatient() {

        log.info("Fetching all the Patients");

        return mapToPatientResponseList(patientRepository.findAll());
    }



    // Updating

    @Transactional
    @Override
    public MedicineResponse updateMedicine(Long adminId, Long medicineId, UpdateInventoryRequest request) {

        log.info("Fetching Admin with Id: {}", adminId);
        if(!userRepository.existsById(adminId)) {
            throw new UsernameNotFoundException("Admin not Found with Id: "+ adminId);
        }


        log.info("Attempt to Update Medicine with Id: {} by Admin: {}", medicineId, adminId);

        MedicineEntity oldMedicine = medicineRepository.findById(medicineId).orElseThrow(() -> new RuntimeException("Medicine Not Found with Id:" + medicineId));

        if(
            request.getBatchNo() != null &&
            !request.getBatchNo().isEmpty() &&
            !request.getBatchNo().equals(oldMedicine.getMedicineInventories().getBatchNo())
        ) {
            oldMedicine.getMedicineInventories().setBatchNo(request.getBatchNo());
        }

        if(
            request.getInStock() != null &&
            request.getInStock() != 0L &&
            !request.getInStock().equals(oldMedicine.getMedicineInventories().getInStock())
        ) {
            oldMedicine.getMedicineInventories().setBatchNo(request.getBatchNo());
        }

        if (
            request.getPerPrice() !=null &&
            request.getPerPrice() != 0L &&
            !request.getPerPrice().equals(oldMedicine.getMedicineInventories().getPricePer())
        ) {
            oldMedicine.getMedicineInventories().setPricePer(request.getPerPrice());
        }

        if (
            request.getManufacturedAt() != null &&
            !request.getManufacturedAt().equals(oldMedicine.getMedicineInventories().getManufacturedAt())
        ) {
            oldMedicine.getMedicineInventories().setManufacturedAt(request.getManufacturedAt());
        }

        if (
            request.getExpireAt() != null &&
            !request.getExpireAt().equals(oldMedicine.getMedicineInventories().getExpireAt())
        ) {
            oldMedicine.getMedicineInventories().setExpireAt(request.getExpireAt());
        }

        final MedicineEntity updatedMedicine = medicineRepository.save(oldMedicine);

        addLog(adminId, "Updated Inventory of Medicine with Id: "+ medicineId);

        return mapToMedicineResponse(updatedMedicine);

    }



//    Deleting

    @Transactional
    @Override
    public void deleteAdminUser(Long adminId) {

        log.warn("Deleting Admin: {}", adminId);

        try {
            userRepository.deleteById(adminId);
        } catch (Exception e) {
            log.error(e.toString());
        }

    }

    @Transactional
    @Override
    public void deleteDoctor(Long doctorId) {
        log.warn("Deleting Doctor Account with Id: {}", doctorId);

        if(doctorRepository.existsByDoctorId(doctorId)) {
            doctorRepository.deleteByDoctorId(doctorId);
        } else {
            throw new UsernameNotFoundException("Doctor Account not Found with Id:" + doctorId);
        }
    }

    @Transactional
    @Override
    public void deletePharma(Long pharmaId) {

        log.warn("Deleting Doctor Account with Id: {}", pharmaId);

        if(pharmaRepository.existsById(pharmaId)) {
            pharmaRepository.deleteById(pharmaId);
        } else {
            throw new UsernameNotFoundException("Pharma Account not Found with Id: "+ pharmaId);
        }
    }

    @Transactional
    @Override
    public void deletePatient(Long patientId) {

        log.warn("Deleting Patient Account with Id: {}", patientId);

        if(patientRepository.findById(patientId).isPresent()) {
            patientRepository.deleteById(patientId);
        } else {
            throw new UsernameNotFoundException("Patient Account not Found with Id:  " + patientId);
        }
    }

    @Transactional
    @Override
    public void removeMedicine(Long adminId, Long medicineId) {

        log.warn("Attempt to Delete Medicine with Id; {}, by Admin with Id: {}", medicineId, adminId);

        log.info("Fetching Admin with Id; {}", medicineId);

        if(!userRepository.existsById(adminId)) {
            throw new UsernameNotFoundException("Admin not Found with Id: {}" + adminId);
        }

        log.info("Fetching Medicine with Id: {}", medicineId);

        if(!medicineRepository.existsById(medicineId)) {
            throw new IllegalArgumentException("Medicine Not Found with Id: "+ medicineId);
        }

        medicineRepository.deleteById(medicineId);

        addLog(adminId, "Deleted Medicine with Id: "+ medicineId);

        log.info("Successfully Deleted Medicine with Id: {} by Admin with Id: {}", medicineId, adminId);


    }

    @Transactional
    @Override
    public void removeAllMedicine(Long adminId) {
        log.info("Fetching Admin with Id: {}", adminId);

        if(!userRepository.existsById(adminId)) {
            throw new UsernameNotFoundException("Admin not Found with Id: "+ adminId);
        }

        log.warn("Deleting all Medicine by Admin with Id: {}", adminId);
        addLog(adminId, "Removed All Medicine");
        medicineRepository.deleteAll();
    }

    @Transactional
    @Override
    public void deleteAllAdmin() {

        log.warn("Deleting All Admin Accounts..!");
        userRepository.deleteAll();
    }

    @Transactional
    @Override
    public void deleteAllDoctors() {
        log.warn("Deleting All Doctor Accounts..!");
        doctorRepository.deleteAll();
    }

    @Transactional
    @Override
    public void deleteAllPharma() {
        log.warn("Deleting All Pharma Accounts..!");
        pharmaRepository.deleteAll();
    }

    @Transactional
    @Override
    public void deleteAllPatients() {
        log.warn("Deleting All Patient Accounts..!");
        patientRepository.deleteAll();
    }

    @Override
    public KeyMetricsResponse keyMetricsResponse() {
        return new KeyMetricsResponse(
                medicineRepository.count(),
                patientRepository.count(),
                doctorRepository.count()
        );
    }


    @Transactional
    @Override
    public LogResponse addLog(Long adminId, String log) {

        final AdminLogEntity newLog = adminLogRepository.save(AdminLogEntity.builder()
                .adminId(adminId)
                .Log(log)
                .build()
        );

        return LogResponse.builder()
                .timeStamp(newLog.getTimeStamp())
                .log(newLog.getLog())
                .build();
    }


    @Override
    public List<LogResponse> listAllLog(Long adminId) {
        return mapToLogResponseList(adminLogRepository.findByAdminId(adminId));
    }


















































    //mappers

    protected UserRequest mapToUserRequest(UsersEntity usersEntity) {
        return mapper.modelMapper().map(usersEntity, UserRequest.class);
    }

    protected UserResponse mapToUserResponse(UsersEntity usersEntity) {
        return mapper.modelMapper().map(usersEntity, UserResponse.class);
    }

    protected  DoctorResponse mapToDoctorResponse(DoctorEntity doctor) {
        return new DoctorResponse(
                doctor.getUser().getUserName(),
                doctor.getUser().getUserPhone(),
                doctor.getDoctorAlternativePhone(),
                doctor.getDoctorSpecialization(),
                doctor.getDoctorQualification(),
                doctor.getDoctorLicenceNo(),
                doctor.getDoctorAvailable()
        );
    }

    protected PatientResponse mapToPatientResponse(PatientEntity patient) {
        return new PatientResponse(

                patient.getUser().getUserName(),
                patient.getUser().getUserEmail(),
                patient.getUser().getUserPhone(),
                patient.getEmergencyPhone(),
                patient.getPatientBloodGroup(),
                patient.getPatientAddress(),
                List.of(),
                List.of(),
                List.of()
        );
    }

    protected MedicineEntity mapToMedicineEntity(AddMedicineRequest request) {
        return MedicineEntity.builder()
                .medicineFullName(request.getMedicineFullName())
                .medicineGenericName(request.getMedicineGenericName())
                .medicineDosageForm(request.getDosageForm())
                .medicineManufacturer(request.getManufacturer())
                .category(request.getMedicineCategories())
                .strengthMg_Ml(request.getStrengthMg_Ml())
                .build();
    }
    protected MedicineInventory mapToInventoryEntity(AddMedicineRequest request) {
        return MedicineInventory.builder()
                .batchNo(request.getInventoryRequest().getBatchNo())
                .manufacturer(request.getInventoryRequest().getManufacturer())
                .pricePer(request.getInventoryRequest().getPerPrice())
                .expireAt(request.getInventoryRequest().getExpireAt())
                .manufacturedAt(request.getInventoryRequest().getManufacturedAt())
                .inStock(request.getInventoryRequest().getInStock())
                .totalQuantity(request.getInventoryRequest().getTotalQuantity())
                .build();
    }

    protected MedicineResponse mapToMedicineResponse(MedicineEntity medicine) {
        return new MedicineResponse(
                medicine.getMedicineFullName(),
                medicine.getCategory(),
                medicine.getStrengthMg_Ml(),
                mapToInventoryResponse(medicine.getMedicineInventories())
        );
    }

    protected InventoryResponse mapToInventoryResponse(MedicineInventory inventory) {
        return new InventoryResponse(
        inventory.getMedicine().getMedicineId(),
        inventory.getPricePer(),
                inventory.getInStock(),
                inventory.getManufacturer(),
                inventory.getManufacturedAt(),
                inventory.getExpireAt()
                );


    }

    protected LogResponse mapToLogResponse(AdminLogEntity adminLog) {
        return new LogResponse(
                adminLog.getTimeStamp(),
                adminLog.getLog()
        );
    }



    // mappers List

    protected  List<UserRequest> mapToUserRequestList(List<UsersEntity> userRequests) {
        return userRequests.stream().map(
                userResquest -> mapToUserRequest(userResquest)
        ).toList();
    }

    protected List<DoctorResponse> mapToDoctorResponseList(List<DoctorEntity> doctorEntities) {
        return doctorEntities.stream().map(
                doctorEntity -> mapToDoctorResponse(doctorEntity)
        ).toList();
    }

    protected List<PatientResponse> mapToPatientResponseList(List<PatientEntity> patientEntities) {
        return patientEntities.stream().map(
                patient -> mapToPatientResponse(patient)
        ).toList();
    }

    protected List<MedicineResponse> mapToMedicineResponseList(List<MedicineEntity> medicineEntities) {
        return medicineEntities.stream().map(
                medicine -> mapToMedicineResponse(medicine)
        ).toList();
    }

    protected List<LogResponse> mapToLogResponseList(List<AdminLogEntity> adminLogEntities) {
        return adminLogEntities.stream().map(
                adminLog -> mapToLogResponse(adminLog)
        ).toList();
    }

}
