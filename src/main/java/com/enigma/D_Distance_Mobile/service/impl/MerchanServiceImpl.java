package com.enigma.D_Distance_Mobile.service.impl;

import com.enigma.D_Distance_Mobile.entity.Merchant;
import com.enigma.D_Distance_Mobile.repository.MerchantRepository;
import com.enigma.D_Distance_Mobile.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
}
