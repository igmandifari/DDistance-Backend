package com.enigma.D_Distance_Mobile.service;

import com.enigma.D_Distance_Mobile.dto.request.NewDistributorRequest;
import com.enigma.D_Distance_Mobile.dto.response.DistributorResponse;

public interface DistributorService {
    DistributorResponse create (NewDistributorRequest request);
    DistributorResponse findAll();
}
