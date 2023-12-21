package com.enigma.D_Distance_Mobile.controller;

import com.enigma.D_Distance_Mobile.dto.request.NewInsuranceRequest;
import com.enigma.D_Distance_Mobile.dto.response.CommonResponse;
import com.enigma.D_Distance_Mobile.dto.response.InsuranceResponse;
import com.enigma.D_Distance_Mobile.service.InsuranceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/insurance")
public class InsuranceController {
    private final InsuranceService insuranceService;

    @GetMapping("/email/send/token")
    public ResponseEntity<?> sendOtp() {
        insuranceService.sendOtp();
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully send otp insurance request")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<InsuranceResponse> insuranceResponses = insuranceService.getAll();
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully get all insurance")
                .statusCode(HttpStatus.OK.value())
                .data(insuranceResponses)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        InsuranceResponse insuranceResponse = insuranceService.findById(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully get insurance by id")
                .statusCode(HttpStatus.OK.value())
                .data(insuranceResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<?> createInsurance(
            @RequestParam MultipartFile ktp,
                @RequestParam MultipartFile agunan,
            @RequestParam MultipartFile siu,
            @RequestParam String otp
    ) {
        NewInsuranceRequest request = NewInsuranceRequest.builder()
                .ktp(ktp)
                .otp(otp)
                .siu(siu)
                .agunan(agunan)
                .build();
        InsuranceResponse insuranceResponse = insuranceService.create(request);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully create new insurance")
                .statusCode(HttpStatus.CREATED.value())
                .data(insuranceResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}/ktp")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<?> getKtp(@PathVariable String id){
        Resource resource = insuranceService.getKtp(id);
        String headerValues = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                .body(resource);
    }
    @GetMapping("/{id}/siu")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<?> getSiu(@PathVariable String id){
        Resource resource = insuranceService.getSiu(id);
        String headerValues = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                .body(resource);
    }
    @GetMapping("/{id}/agunan")
    @PreAuthorize("hasRole('MERCHANT')")
    public ResponseEntity<?> getAgunan(@PathVariable String id){
        Resource resource = insuranceService.getAgunan(id);
        String headerValues = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                .body(resource);
    }
}
