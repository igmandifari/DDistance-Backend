package com.enigma.D_Distance_Mobile.service;

import com.enigma.D_Distance_Mobile.dto.request.NewCreditAnalystRequest;
import com.enigma.D_Distance_Mobile.dto.request.UpdateCreditAnalystRequest;
import com.enigma.D_Distance_Mobile.dto.response.CreditAnalystResponse;

import java.util.List;

public interface CreditAnalystService {
    CreditAnalystResponse create (NewCreditAnalystRequest request);
    CreditAnalystResponse update (UpdateCreditAnalystRequest request);
    List<CreditAnalystResponse> getAll();
    CreditAnalystResponse getById(String id);

}
