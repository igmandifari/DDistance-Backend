package com.enigma.D_Distance_Mobile.controller;

import com.enigma.D_Distance_Mobile.dto.request.NewAdminRequest;
import com.enigma.D_Distance_Mobile.dto.request.UpdateAdminRequest;
import com.enigma.D_Distance_Mobile.dto.request.UpdateDistributorRequest;
import com.enigma.D_Distance_Mobile.dto.response.AdminResponse;
import com.enigma.D_Distance_Mobile.dto.response.CommonResponse;
import com.enigma.D_Distance_Mobile.dto.response.DistributorResponse;
import com.enigma.D_Distance_Mobile.service.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@RequestBody NewAdminRequest request){
        AdminResponse adminResponse = adminService.create(request);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully create admin")
                .statusCode(HttpStatus.CREATED.value())
                .data(adminResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDistributor(@RequestBody UpdateAdminRequest request){
        AdminResponse adminResponse = adminService.update(request);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully update admin")
                .statusCode(HttpStatus.OK.value())
                .data(adminResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll(){
        List<AdminResponse> adminResponses = adminService.getAll();
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully get all admin")
                .statusCode(HttpStatus.OK.value())
                .data(adminResponses)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable String id){
        AdminResponse adminResponse = adminService.getById(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully get by id admin")
                .statusCode(HttpStatus.OK.value())
                .data(adminResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
