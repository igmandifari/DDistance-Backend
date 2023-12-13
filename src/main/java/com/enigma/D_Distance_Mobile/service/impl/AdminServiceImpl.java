package com.enigma.D_Distance_Mobile.service.impl;

import com.enigma.D_Distance_Mobile.entity.Admin;
import com.enigma.D_Distance_Mobile.repository.AdminRepository;
import com.enigma.D_Distance_Mobile.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public void createNew(Admin admin) {
        try {
            log.info("Start createNew");
            adminRepository.saveAndFlush(admin);
            log.info("End createNew");
        } catch (
                DataIntegrityViolationException e) {
            log.error("Error createNew: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "admin already exist");
        }
    }
}
