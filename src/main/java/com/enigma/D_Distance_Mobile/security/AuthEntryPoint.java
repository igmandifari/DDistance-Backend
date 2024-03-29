package com.enigma.D_Distance_Mobile.security;

import com.enigma.D_Distance_Mobile.dto.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .message(authException.getMessage())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .build();

        String commonResponseString = objectMapper.writeValueAsString(commonResponse);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(commonResponseString);
    }
}
