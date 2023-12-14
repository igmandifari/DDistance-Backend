package com.enigma.D_Distance_Mobile.service.impl;

import com.enigma.D_Distance_Mobile.dto.response.DistributorResponse;
import com.enigma.D_Distance_Mobile.dto.response.MerchantResponse;
import com.enigma.D_Distance_Mobile.entity.Distributor;
import com.enigma.D_Distance_Mobile.entity.Merchant;
import com.enigma.D_Distance_Mobile.repository.MerchantRepository;
import com.enigma.D_Distance_Mobile.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MerchanServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;

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
                .email(merchant.getUserCredential().getEmail())
                .pan(merchant.getPan())
                .address(merchant.getAddress())
                .phoneNumber(merchant.getPhoneNumber())
                .name(merchant.getName())
                .balance(merchant.getBalance())
                .build();
    }
}
