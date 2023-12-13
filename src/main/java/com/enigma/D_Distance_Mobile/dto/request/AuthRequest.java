package com.enigma.D_Distance_Mobile.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 6, message = "must be greater than 6 character")
    private String password;
//    @Size(min =6,max = 6, message = "pin must be 6 characters")
//    private String pin;
    private String addres;
    private String phoneNumber;
    private String pan;
    @NotBlank(message = "name is required")
    private String name;
}
