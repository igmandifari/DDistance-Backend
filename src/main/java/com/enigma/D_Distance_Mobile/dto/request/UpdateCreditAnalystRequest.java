package com.enigma.D_Distance_Mobile.dto.request;

import com.enigma.D_Distance_Mobile.constant.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCreditAnalystRequest {
    private String id;
    private String email;
    private String name;
    private String address;
    private String phoneNumber;
    private ERole role;
    private Boolean enabled;
}
