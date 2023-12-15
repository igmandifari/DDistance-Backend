package com.enigma.D_Distance_Mobile.service;

import com.enigma.D_Distance_Mobile.dto.request.NewAdminRequest;
import com.enigma.D_Distance_Mobile.dto.request.UpdateAdminRequest;
import com.enigma.D_Distance_Mobile.dto.response.AdminResponse;
import com.enigma.D_Distance_Mobile.entity.Admin;

import java.util.List;

public interface AdminService {
    void createNew(Admin admin);
//    AdminResponse update(UpdateAdminRequest request);

    AdminResponse create (NewAdminRequest request);
    AdminResponse update(UpdateAdminRequest request);
    List<AdminResponse> getAll();
    AdminResponse getById(String id);
}
