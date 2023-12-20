package com.enigma.D_Distance_Mobile.service.impl;

import com.enigma.D_Distance_Mobile.constant.ERole;
import com.enigma.D_Distance_Mobile.dto.request.NewCreditAnalystRequest;
import com.enigma.D_Distance_Mobile.dto.request.UpdateCreditAnalystRequest;
import com.enigma.D_Distance_Mobile.dto.request.UpdateUserCredentialRequest;
import com.enigma.D_Distance_Mobile.dto.response.CreditAnalystResponse;
import com.enigma.D_Distance_Mobile.entity.CreditAnalyst;
import com.enigma.D_Distance_Mobile.entity.Distributor;
import com.enigma.D_Distance_Mobile.entity.UserCredential;
import com.enigma.D_Distance_Mobile.repository.CreditAnalystRepository;
import com.enigma.D_Distance_Mobile.security.BCryptUtil;
import com.enigma.D_Distance_Mobile.service.CreditAnalystService;
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
public class CreditAnalystServiceImpl implements CreditAnalystService {
    private final CreditAnalystRepository creditAnalystRepository;
    private final ValidationUtil validationUtil;
    private final BCryptUtil bCryptUtil;
    private final UserService userService;
    String password ="password";


    @Transactional(rollbackFor = Exception.class)
    @Override
    public CreditAnalystResponse create(NewCreditAnalystRequest request) {
        try {
            log.info("Start createNew");
            validationUtil.validate(request);
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .role(ERole.ROLE_CREDIT_ANALYST)
                    .password(bCryptUtil.hashPassword(password))
                    .iSenabled(request.getEnabled())
                    .build();


            userService.create(userCredential);

            CreditAnalyst creditAnalyst = CreditAnalyst.builder()
                    .name(request.getName())
                    .phoneNumber(request.getPhoneNumber())
                    .address(request.getAddress())
                    .userCredential(userCredential)
                    .build();
            creditAnalystRepository.saveAndFlush(creditAnalyst);

            return mapToResponse(creditAnalyst);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "credit analyst already exist");
        }
    }



    @Override
    public CreditAnalystResponse update(UpdateCreditAnalystRequest request) {
        validationUtil.validate(request);
        CreditAnalyst creditAnalyst = findById(request.getId());
        creditAnalyst.setAddress(request.getAddress());
        creditAnalyst.setName(request.getName());
        creditAnalyst.setPhoneNumber(request.getPhoneNumber());

        UpdateUserCredentialRequest updateUserCredentialRequest = UpdateUserCredentialRequest.builder()
                .enabled(request.getEnabled())
                .id(creditAnalyst.getUserCredential().getId())
                .role(request.getRole())
                .build();
        userService.update(updateUserCredentialRequest);

        creditAnalystRepository.saveAndFlush(creditAnalyst);
        return mapToResponse(creditAnalyst);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CreditAnalystResponse> getAll() {
        List<CreditAnalyst> creditAnalysts = creditAnalystRepository.findAll();
        return creditAnalysts.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public CreditAnalystResponse getById(String id) {
        CreditAnalyst creditAnalyst = findById(id);
        return mapToResponse(creditAnalyst);
    }
    private CreditAnalyst findById(String id){
        return creditAnalystRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"credit analyst not found")
        );
    }
    private CreditAnalystResponse mapToResponse(CreditAnalyst creditAnalyst) {

        return CreditAnalystResponse.builder()
                .id(creditAnalyst.getId())
                .role(creditAnalyst.getUserCredential().getRole().name())
                .name(creditAnalyst.getName())
                .phoneNumber(creditAnalyst.getPhoneNumber())
                .address(creditAnalyst.getAddress())
                .email(creditAnalyst.getUserCredential().getEmail())
                .enabled(creditAnalyst.getUserCredential().isEnabled())
                .build();
    }
}
