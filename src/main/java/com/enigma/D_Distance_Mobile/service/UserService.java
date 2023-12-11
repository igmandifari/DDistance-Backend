package com.enigma.D_Distance_Mobile.service;

import com.enigma.D_Distance_Mobile.dto.response.UserResponse;
import com.enigma.D_Distance_Mobile.entity.UserCredential;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserCredential loadUserByUserId(String id);
    UserResponse getUserInfo();
}
