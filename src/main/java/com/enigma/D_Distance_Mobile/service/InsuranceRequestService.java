package com.enigma.D_Distance_Mobile.service;

import com.enigma.D_Distance_Mobile.dto.request.NewInsuranceRequest;
import com.enigma.D_Distance_Mobile.dto.response.InsuranceResponse;
import org.springframework.core.io.Resource;

public interface InsuranceRequestService {
    void sendOtp();
    InsuranceResponse create (NewInsuranceRequest request);
    Resource getKtp(String id);
    Resource getSiu(String id);
    Resource getAgunan(String id);
}
