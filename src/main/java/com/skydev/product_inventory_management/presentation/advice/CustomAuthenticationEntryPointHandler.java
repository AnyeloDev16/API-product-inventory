package com.skydev.product_inventory_management.presentation.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skydev.product_inventory_management.presentation.advice.response.ErrorResponse;
import com.skydev.product_inventory_management.service.exceptions.InvalidPasswordException;
import com.skydev.product_inventory_management.util.MessageUtils;
import com.skydev.product_inventory_management.util.TitleMessageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final TitleMessageUtils titleMessageUtils;
    private final MessageUtils messageUtils;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        String errorMessage = switch (authException) {
            case UsernameNotFoundException usernameNotFoundException -> messageUtils.CREDENTIALS_USERNAME_NOT_FOUND;
            case InvalidPasswordException invalidPasswordException -> messageUtils.CREDENTIALS_INVALID_PASSWORD;
            case BadCredentialsException badCredentialsException -> messageUtils.INVALID_CREDENTIALS;
            case null, default -> authException.getMessage();
        };

        ErrorResponse errorResponse = ErrorResponse.builder()
                .title(titleMessageUtils.AUTHENTICATION_ERROR)
                .errorCode(HttpStatus.UNAUTHORIZED.value())
                .errors(List.of(errorMessage))
                .errorDate(LocalDateTime.now())
                .build();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
