package com.enigma.D_Distance_Mobile.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


@Component
@RequiredArgsConstructor
public class OneTimePasswordHelper {
    @Value("${app.warung-makan-bahari.jwt-secret}")
    private String key;
    private String algorithm = "HmacSHA256";
    @Value("${app.warung-makan-bahari.app-name}")
    private String appName;
    public String generateHashOtp(String token)   {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKeySpec);
            return bytesToHex(mac.doFinal(token.getBytes()));
        }catch (NoSuchAlgorithmException | InvalidKeyException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    public String createRandomOneTimePassword() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = Character.forDigit(v >>> 4, 16);
            hexChars[j * 2 + 1] = Character.forDigit(v & 0x0F, 16);
        }
        return new String(hexChars);
    }


//    public boolean verifyOtp(String token) {
//
//    }
}

