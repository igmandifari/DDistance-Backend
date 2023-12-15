package com.enigma.D_Distance_Mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponse {
    private  String id;
    private String email;
    private Boolean enabled;
    private String name;
    private String role;
    private String address;
    private String phoneNumber;
}
