package com.enigma.D_Distance_Mobile.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCreditAnalystRequest {
    private String email;
    private String name;
    private String address;
    private String phoneNumber;
    private Boolean enabled;
}
