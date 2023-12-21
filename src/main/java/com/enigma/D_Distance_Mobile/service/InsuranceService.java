package com.enigma.D_Distance_Mobile.service;

import com.enigma.D_Distance_Mobile.dto.request.NewInsuranceRequest;
import com.enigma.D_Distance_Mobile.dto.response.InsuranceResponse;
import org.springframework.core.io.Resource;

import java.util.List;

public interface InsuranceService {
    void sendOtp();
    InsuranceResponse create (NewInsuranceRequest request);
    Resource getKtp(String id);
    Resource getSiu(String id);
    Resource getAgunan(String id);
    List<InsuranceResponse> getAll();
    InsuranceResponse findById(String id);
}
