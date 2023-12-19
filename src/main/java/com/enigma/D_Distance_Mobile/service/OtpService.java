package com.enigma.D_Distance_Mobile.service;

import com.enigma.D_Distance_Mobile.entity.OneTimePassword;
import com.enigma.D_Distance_Mobile.entity.UserCredential;

public interface OtpService {
    String createOtp(UserCredential userCredential, Integer time);
    OneTimePassword findConfirmationToken(String confirmationToken);
    void delete(String id);
}
