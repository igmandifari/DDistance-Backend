package com.enigma.D_Distance_Mobile.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewInsuranceRequest {
    private MultipartFile ktp;
    private MultipartFile siu;
    private MultipartFile agunan;
    private String otp;
}
