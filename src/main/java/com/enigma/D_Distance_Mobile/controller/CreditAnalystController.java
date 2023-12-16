package com.enigma.D_Distance_Mobile.controller;

import com.enigma.D_Distance_Mobile.dto.request.NewCreditAnalystRequest;
import com.enigma.D_Distance_Mobile.dto.request.UpdateCreditAnalystRequest;
import com.enigma.D_Distance_Mobile.dto.response.CommonResponse;
import com.enigma.D_Distance_Mobile.dto.response.CreditAnalystResponse;
import com.enigma.D_Distance_Mobile.service.CreditAnalystService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/creditAnalyst")
@RequiredArgsConstructor
public class CreditAnalystController {
    private final CreditAnalystService creditAnalystService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody NewCreditAnalystRequest request){
        CreditAnalystResponse creditAnalystResponse = creditAnalystService.create(request);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully create credit analyst")
                .statusCode(HttpStatus.CREATED.value())
                .data(creditAnalystResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<?> update(@RequestBody UpdateCreditAnalystRequest request){
        CreditAnalystResponse creditAnalystResponse = creditAnalystService.update(request);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully update credit analyst")
                .statusCode(HttpStatus.OK.value())
                .data(creditAnalystResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll(){
        List<CreditAnalystResponse> creditAnalystResponses = creditAnalystService.getAll();
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully get all credit analyst")
                .statusCode(HttpStatus.OK.value())
                .data(creditAnalystResponses)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getById(@PathVariable String id){
        CreditAnalystResponse creditAnalystResponse = creditAnalystService.getById(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully get all credit analyst")
                .statusCode(HttpStatus.OK.value())
                .data(creditAnalystResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
