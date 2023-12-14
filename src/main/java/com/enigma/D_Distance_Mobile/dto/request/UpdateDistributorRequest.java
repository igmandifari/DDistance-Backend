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
    private String name;
    @NotBlank(message = "address is required")
    private String address;
    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;
    @NotBlank(message = "role is required")
    private ERole role;
    @NotBlank(message = "status is required")
    private Boolean iSenabled;
    @NotBlank(message = "user credential id is required")
    private String UserCredentialId;
}
//public class UpdateTableRequest {
//    @NotBlank(message = "id is required")
//    private String id;
//    @NotBlank(message = "name is required")
//    private String name;
//    @NotNull(message = "status is required")
//    private String status;
//}

//Nama
//Alamat
//No. Telp
//Tipe User
//Status User
