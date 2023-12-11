package com.enigma.D_Distance_Mobile.service.impl;

import com.enigma.D_Distance_Mobile.constant.ERole;
import com.enigma.D_Distance_Mobile.dto.request.AuthRequest;
import com.enigma.D_Distance_Mobile.dto.response.RegisterResponse;
import com.enigma.D_Distance_Mobile.entity.Merchant;
import com.enigma.D_Distance_Mobile.entity.UserCredential;
import com.enigma.D_Distance_Mobile.repository.UserCredentialRepository;
import com.enigma.D_Distance_Mobile.security.BCryptUtil;
import com.enigma.D_Distance_Mobile.security.JwtUtil;
import com.enigma.D_Distance_Mobile.service.AuthService;
import com.enigma.D_Distance_Mobile.service.MerchantService;
import com.enigma.D_Distance_Mobile.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final BCryptUtil bCryptUtil;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;
    private final MerchantService merchantService;



    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerMerchant(AuthRequest request) {
        try {
            validationUtil.validate(request);
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(bCryptUtil.hashPassword(request.getPassword()))
                    .role(ERole.ROLE_MERCHANT)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);
            Merchant merchant = Merchant.builder()
                    .name(request.getName())
                    .userCredential(userCredential)
                    .build();
            merchantService.save(merchant);
            return mapToResponse(userCredential);
        }catch (DataIntegrityViolationException e){
            log.error("Error registerCustomer: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exist");
        }
    }

    private RegisterResponse mapToResponse(UserCredential userCredential) {
        return RegisterResponse.builder()
                .email(userCredential.getEmail())
                .role(userCredential.getRole().name())
                .build();
    }
}
