package com.enigma.D_Distance_Mobile.service;

import com.enigma.D_Distance_Mobile.dto.request.AuthRequest;
import com.enigma.D_Distance_Mobile.dto.request.LoginRequest;
import com.enigma.D_Distance_Mobile.dto.response.LoginResponse;
import com.enigma.D_Distance_Mobile.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerMerchant(AuthRequest request);
    RegisterResponse confirmEmail(String token);
    LoginResponse login(LoginRequest request);
}
