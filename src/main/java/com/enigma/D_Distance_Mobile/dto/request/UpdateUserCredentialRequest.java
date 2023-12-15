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
public class UpdateUserCredentialRequest {
    private String id;
    private Boolean enabled;
    private ERole role;
}
