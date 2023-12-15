package com.enigma.D_Distance_Mobile.service.impl;

import com.enigma.D_Distance_Mobile.dto.request.UpdateMerchantRequest;
import com.enigma.D_Distance_Mobile.dto.response.MerchantResponse;
import com.enigma.D_Distance_Mobile.dto.response.MerchantResponse;
import com.enigma.D_Distance_Mobile.entity.Merchant;
import com.enigma.D_Distance_Mobile.entity.Merchant;
import com.enigma.D_Distance_Mobile.entity.UserCredential;
import com.enigma.D_Distance_Mobile.repository.MerchantRepository;
import com.enigma.D_Distance_Mobile.repository.UserCredentialRepository;
import com.enigma.D_Distance_Mobile.service.MerchantService;
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
public class MerchanServiceImpl implements MerchantService {
    private final ValidationUtil validationUtil;
    private final MerchantRepository merchantRepository;
    private final UserCredentialRepository userCredentialRepository;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Merchant save(Merchant merchant) {
        try {
            return merchantRepository.saveAndFlush(merchant);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "phone number already exist");
        }
    }

    @Override
    public MerchantResponse update(UpdateMerchantRequest request) {
        try {
            validationUtil.validate(request);
            Merchant merchant = merchantRepository.findById(request.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "merchant not found"));
            merchant.setName(request.getName());
            merchant.setAddress(request.getAddress());
            merchant.setPhoneNumber(request.getPhoneNumber());
            merchantRepository.saveAndFlush(merchant);
            UserCredential userCredential = userCredentialRepository.findById(merchant.getUserCredential().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "merchant not found"));
            userCredential.setRole(request.getRole());
            userCredential.setISenabled(request.getEnabled());
            userCredentialRepository.saveAndFlush(userCredential);

            return mapToResponse(merchant);
        }catch (DataIntegrityViolationException e) {
            log.error("Error update: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "data already exist");
        }
    }
    @Override
    public List<MerchantResponse> findAll() {
        List<Merchant> merchants = merchantRepository.findAll();
        return merchants.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public MerchantResponse getById(String id){
        Merchant merchant = merchantRepository
                .findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "merchant not found"));
        return mapToResponse(merchant);
    }

    private MerchantResponse mapToResponse(Merchant merchant) {
        return MerchantResponse.builder()
                .id(merchant.getId())
                .email(merchant.getUserCredential().getEmail())
                .pan(merchant.getPan())
                .address(merchant.getAddress())
                .phoneNumber(merchant.getPhoneNumber())
                .name(merchant.getName())
                .balance(merchant.getBalance())
                .enabled(merchant.getUserCredential().isEnabled())
                .build();
    }
}
