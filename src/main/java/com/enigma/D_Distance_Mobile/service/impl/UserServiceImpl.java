package com.enigma.D_Distance_Mobile.service.impl;


import com.enigma.D_Distance_Mobile.dto.request.UpdateUserCredentialRequest;
import com.enigma.D_Distance_Mobile.dto.response.UserResponse;
import com.enigma.D_Distance_Mobile.entity.UserCredential;
import com.enigma.D_Distance_Mobile.repository.UserCredentialRepository;
import com.enigma.D_Distance_Mobile.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserCredentialRepository userCredentialRepository;

    @Override
    public UserCredential loadUserByUserId(String id) {
        log.info("Start loadByUserId");
        UserCredential userCredential = userCredentialRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("invalid credential"));
        log.info("End loadByUserId");
        return userCredential;
    }

    @Override
    public UserResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        UserCredential userCredential = (UserCredential) authentication.getPrincipal();
        return UserResponse.builder()
                .email(userCredential.getUsername())
                .role(userCredential.getRole().name())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserCredential create(UserCredential userCredential) {
        try{
            return userCredentialRepository.saveAndFlush(userCredential);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exist");
        }
    }

    @Override
    public UserCredential update(UpdateUserCredentialRequest request) {
        UserCredential userCredential = findById(request.getId());
        userCredential.setISenabled(request.getEnabled());
        userCredential.setRole(request.getRole());


        return userCredentialRepository.saveAndFlush(userCredential);
    }

    private UserCredential findById(String id){
        return userCredentialRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found")
        );
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Start loadUserByUsername");
        UserCredential userCredential = userCredentialRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("invalid credential"));
        log.info("End loadUserByUsername");

        return UserCredential.builder()
                .id(userCredential.getId())
                .email(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRole())
                .iSenabled(userCredential.getISenabled())
                .build();
    }
}
