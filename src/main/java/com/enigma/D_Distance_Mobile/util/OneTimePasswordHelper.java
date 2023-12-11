package com.enigma.D_Distance_Mobile.util;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.function.Supplier;


public class OneTimePasswordHelper {

    public static String createRandomOneTimePassword() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }
}
