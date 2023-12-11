package com.enigma.D_Distance_Mobile.service;

import com.enigma.D_Distance_Mobile.entity.OneTimePassword;
import com.enigma.D_Distance_Mobile.entity.UserCredential;

public interface OtpService {
    OneTimePassword createOtp(UserCredential userCredential);
}
