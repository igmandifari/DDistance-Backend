package com.enigma.D_Distance_Mobile.service.impl;

import com.enigma.D_Distance_Mobile.constant.ERole;
import com.enigma.D_Distance_Mobile.dto.request.NewAdminRequest;
import com.enigma.D_Distance_Mobile.dto.request.UpdateAdminRequest;
import com.enigma.D_Distance_Mobile.dto.request.UpdateUserCredentialRequest;
import com.enigma.D_Distance_Mobile.dto.response.AdminResponse;
import com.enigma.D_Distance_Mobile.entity.Admin;
import com.enigma.D_Distance_Mobile.entity.UserCredential;
import com.enigma.D_Distance_Mobile.repository.AdminRepository;
import com.enigma.D_Distance_Mobile.repository.UserCredentialRepository;
import com.enigma.D_Distance_Mobile.security.BCryptUtil;
import com.enigma.D_Distance_Mobile.service.AdminService;
import com.enigma.D_Distance_Mobile.service.UserService;
import com.enigma.D_Distance_Mobile.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final BCryptUtil bCryptUtil;
    private final UserService userService;
    private final ValidationUtil validationUtil;


    String password = "password";
    @Override
    public void createNew(Admin admin) {
        try {
            log.info("Start createNew");
            adminRepository.saveAndFlush(admin);
            log.info("End createNew");
        } catch (
                DataIntegrityViolationException e) {
            log.error("Error createNew: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "admin already exist");
        }
    }

    @Override
    public AdminResponse create(NewAdminRequest request) {
        try {
            log.info("Start createNew");
            validationUtil.validate(request);
            UserCredential userCredential = UserCredential.builder()
                    .password(bCryptUtil.hashPassword(password))
                    .role(ERole.ROLE_ADMIN)
                    .email(request.getEmail())
                    .iSenabled(true)
                    .build();
            userService.create(userCredential);


            Admin admin = Admin.builder()
                    .name(request.getName())
                    .phoneNumber(request.getPhoneNumber())
                    .address(request.getAddress())
                    .userCredential(userCredential)
                    .build();

            adminRepository.saveAndFlush(admin);
            log.info("End createNew");
            return mapToResponse(admin);
        } catch (
                DataIntegrityViolationException e) {
            log.error("Error createNew: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "admin already exist");
        }
    }

    @Override
    public AdminResponse update(UpdateAdminRequest request) {
        Admin admin = findById(request.getId());
        admin.setAddress(request.getAddress());
        admin.setName(request.getName());
        admin.setPhoneNumber(request.getPhoneNumber());

        UpdateUserCredentialRequest updateUserCredentialRequest = UpdateUserCredentialRequest.builder()
                .enabled(request.getEnabled())
                .id(admin.getUserCredential().getId())
                .role(request.getRole())
                .build();

        adminRepository.saveAndFlush(admin);
//        admin

        return mapToResponse(admin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AdminResponse> getAll() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public AdminResponse getById(String id) {
        Admin admin = findById(id);
        return mapToResponse(admin);
    }


    private Admin findById(String id){
        return adminRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,"admin not found")
        );
    }

    private AdminResponse mapToResponse(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getId())
                .enabled(admin.getUserCredential().isEnabled())
                .address(admin.getAddress())
                .role(admin.getUserCredential().getRole().name())
                .phoneNumber(admin.getPhoneNumber())
                .email(admin.getUserCredential().getEmail())
                .name(admin.getName())
                .build();
    }
}
