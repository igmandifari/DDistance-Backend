package com.enigma.D_Distance_Mobile.service;

import com.enigma.D_Distance_Mobile.dto.request.UpdateMerchantRequest;
import com.enigma.D_Distance_Mobile.dto.response.MerchantResponse;
import com.enigma.D_Distance_Mobile.entity.Merchant;
import com.enigma.D_Distance_Mobile.entity.UserCredential;

import java.util.List;

public interface MerchantService {
    Merchant save(Merchant merchant);

    List<MerchantResponse> findAll();
    Merchant findByUser(UserCredential userCredential);

    MerchantResponse getById(String id);

    MerchantResponse update(UpdateMerchantRequest request);
}
