package com.enigma.D_Distance_Mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantResponse {
    private String email;
    private String name;
    private String address;
    private String pan;
    private String phoneNumber;
    private Long balance;
}
