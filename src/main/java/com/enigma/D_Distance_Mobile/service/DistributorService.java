package com.enigma.D_Distance_Mobile.service;

import com.enigma.D_Distance_Mobile.dto.request.NewDistributorRequest;
import com.enigma.D_Distance_Mobile.dto.request.UpdateDistributorRequest;
import com.enigma.D_Distance_Mobile.dto.response.DistributorResponse;

import java.util.List;

public interface DistributorService {
    DistributorResponse create (NewDistributorRequest request);

    DistributorResponse update (UpdateDistributorRequest request);
    List<DistributorResponse> findAll();
    DistributorResponse getById(String id);
}
