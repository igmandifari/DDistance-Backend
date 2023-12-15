package com.enigma.D_Distance_Mobile.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewDistributorRequest {
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 6, message = "must be greater than 6 character")
//    private String password;
    private String companyId;
    private Boolean enabled;
    private String name;
    private String address;
    private String pan;
    private String phoneNumber;
}
