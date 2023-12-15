package com.enigma.D_Distance_Mobile.controller;

import com.enigma.D_Distance_Mobile.dto.request.UpdateMerchantRequest;
import com.enigma.D_Distance_Mobile.dto.response.CommonResponse;
import com.enigma.D_Distance_Mobile.dto.response.MerchantResponse;
import com.enigma.D_Distance_Mobile.dto.response.MerchantResponse;
import com.enigma.D_Distance_Mobile.entity.Merchant;
import com.enigma.D_Distance_Mobile.service.MerchantService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/merchant")
public class MerchantController {
    private final MerchantService merchantService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllMerchant(){
        List<MerchantResponse> merchantResponses = merchantService.findAll();
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully get all merchant")
                .statusCode(HttpStatus.OK.value())
                .data(merchantResponses)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<?> getMerchantById(@PathVariable String id){
        MerchantResponse merchantResponse = merchantService.getById(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully get merchant")
                .statusCode(HttpStatus.OK.value())
                .data(merchantResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateMerchant(@RequestBody UpdateMerchantRequest request){
        MerchantResponse merchantResponse = merchantService.update(request);
        CommonResponse<MerchantResponse> response = CommonResponse.<MerchantResponse>builder()
                .message("successfully update merchant")
                .statusCode(HttpStatus.OK.value())
                .data(merchantResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
