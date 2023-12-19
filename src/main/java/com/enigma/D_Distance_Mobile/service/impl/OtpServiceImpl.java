package com.enigma.D_Distance_Mobile.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.enigma.D_Distance_Mobile.entity.OneTimePassword;
import com.enigma.D_Distance_Mobile.entity.UserCredential;
import com.enigma.D_Distance_Mobile.repository.OneTimePasswordRepository;
import com.enigma.D_Distance_Mobile.security.BCryptUtil;
import com.enigma.D_Distance_Mobile.security.JwtUtil;
import com.enigma.D_Distance_Mobile.service.OtpService;
import com.enigma.D_Distance_Mobile.util.OneTimePasswordHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {
    private final OneTimePasswordRepository repository;
    private final OneTimePasswordHelper oneTimePasswordHelper;





    @Transactional(rollbackFor = Exception.class)
    @Override
    public String createOtp(UserCredential userCredential, Integer time) {
        try {
            String otp = oneTimePasswordHelper.createRandomOneTimePassword();
            log.info(otp);
            OneTimePassword oneTimePassword = OneTimePassword.builder()
                    .user(userCredential)
                    .token(oneTimePasswordHelper.generateHashOtp(otp))
                    .expiryDate(calculateExpiryDate(time))
                    .build();
             repository.saveAndFlush(oneTimePassword);
            return otp;
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "otp already exist");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public OneTimePassword findConfirmationToken(String confirmationToken) {
        String otp = oneTimePasswordHelper.generateHashOtp(confirmationToken);
        return repository.findOneTimePasswordByToken((otp)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "One Time Password not found")
        );
    }

    @Override
    public void delete(String id) {
        OneTimePassword oneTimePassword = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "One Time Password not found")
        );
        repository.delete(oneTimePassword);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    @Scheduled(fixedRate = 60000)
    public void cleanupExpiredOtp(){
        Date currentDate = new Date();
        List<OneTimePassword> expiredOtp = repository.findByExpiryDateBefore(currentDate);
        log.info("Delete Otp");
        repository.deleteAll(expiredOtp);
    }


}
