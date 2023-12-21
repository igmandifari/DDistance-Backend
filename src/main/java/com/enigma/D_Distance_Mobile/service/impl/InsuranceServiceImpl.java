package com.enigma.D_Distance_Mobile.service.impl;

import com.enigma.D_Distance_Mobile.constant.EInstallemnt;
import com.enigma.D_Distance_Mobile.dto.request.NewInsuranceRequest;
import com.enigma.D_Distance_Mobile.dto.response.InsuranceResponse;
import com.enigma.D_Distance_Mobile.entity.Insurance;
import com.enigma.D_Distance_Mobile.entity.Merchant;
import com.enigma.D_Distance_Mobile.entity.OneTimePassword;
import com.enigma.D_Distance_Mobile.entity.UserCredential;
import com.enigma.D_Distance_Mobile.repository.InsuranceRepository;
import com.enigma.D_Distance_Mobile.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {
    private final EmailService emailService;
    private final OtpService otpService;
    private final InsuranceRepository insuranceRepository;
    private final MerchantService merchantService;
    private final FileService fileService;

    @Value("${spring.mail.username}")
    private String formMail;



    @Override
    public void sendOtp() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserCredential userCredential = (UserCredential) authentication.getPrincipal();
        Integer time = 5;
        String otp = otpService.createOtp(userCredential,time);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(formMail);
        mailMessage.setTo(userCredential.getEmail());
        mailMessage.setSubject("This Is Otp");
        mailMessage.setText("Your OTP Insurance Request: "
              + otp);
        emailService.sendEmail(mailMessage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public InsuranceResponse create(NewInsuranceRequest request) {
        OneTimePassword confirmationToken = otpService.findConfirmationToken(request.getOtp());
        Merchant merchant = merchantService.findByUser(confirmationToken.getUser());
        String ktp = fileService.createFile(request.getKtp());
        String siu = fileService.createFile(request.getSiu());
        String agunan = fileService.createFile(request.getAgunan());

        Insurance insurance = Insurance.builder()
                .merchant(merchant)
                .siu(siu)
                .ktp(ktp)
                .agunan(agunan)
                .statusSurvey(EInstallemnt.DALAM_PROSES)
                .waktuPengajuan(LocalDateTime.now())
                .build();
        insuranceRepository.saveAndFlush(insurance);
        return mapToResponse(insurance);
    }

    @Override
    public Resource getKtp(String id) {
        Insurance insurance = getInsurance(id);
        return fileService.urlImage(insurance.getKtp());
    }


    @Override
    public Resource getSiu(String id) {
        Insurance insurance = getInsurance(id);
        return fileService.urlImage(insurance.getSiu());
    }

    @Override
    public Resource getAgunan(String id) {
        Insurance insurance = getInsurance(id);
        return fileService.urlImage(insurance.getAgunan());
    }

    @Transactional(readOnly = true)
    @Override
    public List<InsuranceResponse> getAll() {
        List<Insurance> insurances = insuranceRepository.findAll();
        return insurances.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public InsuranceResponse findById(String id) {
        Insurance insurance = getInsurance(id);
        return mapToResponse(insurance);
    }

    private Insurance getInsurance(String id) {
        return insuranceRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "insurance not found")
        );
    }

    private InsuranceResponse mapToResponse(Insurance insurance) {
        String ktp = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/insurance/")
                .path(insurance.getId())
                .path("/ktp")
                .toUriString();
        String agunan = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/insurance/")
                .path(insurance.getId())
                .path("/agunan")
                .toUriString();
        String siu = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/insurance/")
                .path(insurance.getId())
                .path("/siu")
                .toUriString();
        LocalDate date= insurance.getWaktuPengajuan().toLocalDate();
        return InsuranceResponse.builder()
                .id(insurance.getId())
                .urlAgunan(agunan)
                .urlSiu(siu)
                .urlKtp(ktp)
                .date(date.toString())
                .status(insurance.getStatusSurvey().name())
                .nameStore(insurance.getMerchant().getName())
                .rejection(insurance.getRejection())
                .build();
    }
}
