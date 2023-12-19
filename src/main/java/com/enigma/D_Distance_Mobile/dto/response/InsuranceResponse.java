package com.enigma.D_Distance_Mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceResponse {
    private String id;
    private String date;
    private String nameStore;
    private String status;
    private String urlKtp;
    private String urlSiu;
    private String urlAgunan;
    private String rejection;
}
