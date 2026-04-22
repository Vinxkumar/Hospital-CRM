package com.example.hospitalCrm.service.serviceImpl;

import com.example.hospitalCrm.configuration.ModelMapperConfig;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorRequest;
import com.example.hospitalCrm.dtos.DoctorDto.DoctorResponse;
import com.example.hospitalCrm.dtos.PatientDto.PatientRequest;
import com.example.hospitalCrm.dtos.PatientDto.PatientResponse;
import com.example.hospitalCrm.dtos.UserDto.UserProject;
import com.example.hospitalCrm.dtos.UserDto.UserRequest;
import com.example.hospitalCrm.dtos.UserDto.UserResponse;
import com.example.hospitalCrm.entity.DoctorEntity;
import com.example.hospitalCrm.entity.PatientEntity;
import com.example.hospitalCrm.entity.UsersEntity;
import com.example.hospitalCrm.respository.DoctorRepository;
import com.example.hospitalCrm.respository.PatientRepository;
import com.example.hospitalCrm.respository.PharmaRepository;
import com.example.hospitalCrm.respository.UserRepository;
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

//        if(newAdmin == null) {
//            throw new RuntimeException("Unable to Create a New Admin Account for User: " + userRequest.getUserEmail());
//        }
        final String token = jwtUtil.generateToken(newAdmin);

        return new UserResponse(
                token,
                newAdmin.getUserName(),
                newAdmin.getUserEmail(),
                newAdmin.getUserPhone(),
                LocalDateTime.now()
        );

    }

    @Transactional
    @Override
    public DoctorResponse createNewDoctor(DoctorRequest doctorRequest) {

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
//
//        if(newUser == null) {
//            throw new RuntimeException("Unable to Create a New User Account for User: " + doctorRequest.getUser().getUserEmail());
//        }

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

//        if(newDoctor == null) {
//            throw new RuntimeException("Failed to Create a New Doctor Account for User with Email: "+ newUser.getUserEmail());
//        }

        log.info("Doctor Account Create Successfully with Email: {}", newDoctor.getUser().getUserEmail());

        final String token = jwtUtil.generateToken(newUser);

        return new DoctorResponse(
                token,
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
    public UserResponse createNewPharma(UserRequest userRequest) {
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

//        if(newPharma == null) {
//            throw new RuntimeException("Unable to Create a New Admin Account for User: " + userRequest.getUserEmail());
//        }
        final String token = jwtUtil.generateToken(newPharma);

        return new UserResponse(
                token,
                newPharma.getUserName(),
                newPharma.getUserEmail(),
                newPharma.getUserPhone(),
                LocalDateTime.now());
    }

    @Transactional
    @Override
    public PatientResponse createNewPatient(PatientRequest patientRequest) {

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

        log.info("Generating Token for User: {}", patientRequest.getUser().getUserEmail());

        final String token = jwtUtil.generateToken(newPatient);

        if(token == null || token.isEmpty()) {
            throw new RuntimeException("Failed to Create Token for User: "+ patientRequest.getUser().getUserEmail());
        }

        log.info("Patient Account Created Successfully....!");

        return new PatientResponse(
                token,
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



    // Listing

    @Override
    public UserResponse fetchByAdminUserId(Long id) {
        log.info("Fetching Admin User By Id");
        UserProject userProject =  userRepository.findByUserRole(id, Role.ADMIN);
        return new UserResponse(
                "",
                userProject.getUserName(),
                userProject.getUserEmail(),
                userProject.getUserPhone(),
                LocalDateTime.now()
        );
    }

    @Override
    public DoctorResponse fetchByDoctorId(Long id) {
        log.info("Fetcginh Doctor by Id: {}", id);
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
                LocalDateTime.now()
        );
    }

    @Override
    public PatientResponse fetchByPatientId(Long id) {
        log.info("Fetching Patient by Id: {}", id);
        return mapToPatientResponse(patientRepository.findById(id).orElseThrow(()-> new RuntimeException("User Not Found")));
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

    @Override
    public void deleteAllAdmin() {

        log.warn("Deleting All Admin Accounts..!");
        userRepository.deleteAll();
    }

    @Override
    public void deleteAllDoctors() {
        log.warn("Deleting All Doctor Accounts..!");
        doctorRepository.deleteAll();
    }

    @Override
    public void deleteAllPharma() {
        log.warn("Deleting All Pharma Accounts..!");
        pharmaRepository.deleteAll();
    }

    @Override
    public void deleteAllPatients() {
        log.warn("Deleting All Patient Accounts..!");
        patientRepository.deleteAll();
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
                "",
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
                "",
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
}
