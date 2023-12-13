package com.enigma.D_Distance_Mobile.service.impl;

import com.enigma.D_Distance_Mobile.constant.ERole;
import com.enigma.D_Distance_Mobile.dto.request.NewDistributorRequest;
import com.enigma.D_Distance_Mobile.dto.response.DistributorResponse;
import com.enigma.D_Distance_Mobile.entity.Distributor;
import com.enigma.D_Distance_Mobile.entity.UserCredential;
import com.enigma.D_Distance_Mobile.repository.DistributorRepository;
import com.enigma.D_Distance_Mobile.repository.UserCredentialRepository;
import com.enigma.D_Distance_Mobile.security.BCryptUtil;
import com.enigma.D_Distance_Mobile.service.DistributorService;
import com.enigma.D_Distance_Mobile.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistributorServiceImpl implements DistributorService {
    private final ValidationUtil validationUtil;
    private final DistributorRepository distributorRepository;
    private final UserCredentialRepository userCredentialRepository;
    private final BCryptUtil bCryptUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public DistributorResponse create(NewDistributorRequest request) {
        try {
            log.info("Start createNew");
            validationUtil.validate(request);

            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .role(ERole.ROLE_DISTRIBUTOR)
                    .password(bCryptUtil.hashPassword(request.getPassword()))
                    .iSenabled(request.getEnabled())
                    .build();


            userCredentialRepository.saveAndFlush(userCredential);


            Distributor distributor = Distributor.builder()
                    .userCredential(userCredential)
                    .name(request.getName())
                    .companyId(request.getCompanyId())
                    .phoneNumber(request.getPhoneNumber())
                    .address(request.getAddress())
                    .pan(request.getPan())
                    .build();

            distributorRepository.saveAndFlush(distributor);
            return mapToResponse(distributor);
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "distributor already exist");
        }
    }



    @Override
    public DistributorResponse findAll() {
        return null;
    }
    private DistributorResponse mapToResponse(Distributor distributor) {
       return DistributorResponse.builder()
                .companyId(distributor.getCompanyId())
                .email(distributor.getUserCredential().getEmail())
                .pan(distributor.getPan())
                .address(distributor.getAddress())
               .phoneNumber(distributor.getPhoneNumber())
               .name(distributor.getName())
                .build();
    }
}
