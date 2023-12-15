package com.enigma.D_Distance_Mobile.dto.request;

import com.enigma.D_Distance_Mobile.constant.ERole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAdminRequest {
    private String id;
    //    @NotBlank(message = "password is required")
//    @Size(min = 6, message = "must be greater than 6 character")
//    private String password;
    private Boolean enabled;
    private String name;
    private String address;
    private ERole role;
    private String phoneNumber;
}
