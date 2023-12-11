package com.enigma.D_Distance_Mobile.service.impl;

import com.enigma.D_Distance_Mobile.entity.OneTimePassword;
import com.enigma.D_Distance_Mobile.entity.UserCredential;
import com.enigma.D_Distance_Mobile.repository.OneTimePasswordRepository;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {
    private final OneTimePasswordRepository repository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OneTimePassword createOtp(UserCredential userCredential) {
        try {
            String otp = OneTimePasswordHelper.createRandomOneTimePassword();
            OneTimePassword oneTimePassword = OneTimePassword.builder()
                    .user(userCredential)
                    .token(otp)
                    .expiryDate(calculateExpiryDate(60*24))
                    .build();
            return repository.saveAndFlush(oneTimePassword);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "otp already exist");
        }
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
