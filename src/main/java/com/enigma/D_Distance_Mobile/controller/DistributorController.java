package com.enigma.D_Distance_Mobile.controller;

import com.enigma.D_Distance_Mobile.dto.request.NewDistributorRequest;
import com.enigma.D_Distance_Mobile.dto.request.UpdateDistributorRequest;
import com.enigma.D_Distance_Mobile.dto.response.CommonResponse;
import com.enigma.D_Distance_Mobile.dto.response.DistributorResponse;
import com.enigma.D_Distance_Mobile.service.DistributorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/distributor")
public class DistributorController {
    private final DistributorService distributorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody NewDistributorRequest request){
        DistributorResponse distributorResponse = distributorService.create(request);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully create distributor")
                .statusCode(HttpStatus.OK.value())
                .data(distributorResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllDistributor(){
        List<DistributorResponse> distributorResponses = distributorService.findAll();
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully get all distributor")
                .statusCode(HttpStatus.OK.value())
                .data(distributorResponses)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> getDistributorById(@PathVariable String id){
        DistributorResponse distributorResponse = distributorService.getById(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully get distributor")
                .statusCode(HttpStatus.OK.value())
                .data(distributorResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateDistributor(@RequestBody UpdateDistributorRequest request){
        DistributorResponse distributorResponse = distributorService.update(request);
        CommonResponse<DistributorResponse> response = CommonResponse.<DistributorResponse>builder()
                .message("successfully update distributor")
                .statusCode(HttpStatus.OK.value())
                .data(distributorResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
