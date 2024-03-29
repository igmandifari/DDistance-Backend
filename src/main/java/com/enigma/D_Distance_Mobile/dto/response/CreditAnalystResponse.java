package com.enigma.D_Distance_Mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditAnalystResponse {
    private String id;
    private String email;
    private String name;
    private String address;
    private Boolean enabled;
    private String phoneNumber;
    private String role;
}
