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
public class UpdateDistributorRequest {
    @NotBlank(message = "id is required")
    private String id;
    @NotBlank(message = "name is required")
    private String companyId;
    private String name;
    @NotBlank(message = "address is required")
    private String address;
    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;
    private Boolean enabled;
}
