package com.skydev.product_inventory_management.presentation.advice;

import java.time.LocalDateTime;
import java.util.List;

import com.skydev.product_inventory_management.util.TitleMessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.skydev.product_inventory_management.presentation.advice.response.ErrorResponse;
import com.skydev.product_inventory_management.service.exceptions.EntityNotFoundException;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

    private final TitleMessageUtil titleMessageUtil;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> getEntityNotFoundException(EntityNotFoundException enfe){

        return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse
                                .builder()
                                .title(titleMessageUtil.RESOURCE_NOT_FOUND)
                                .errorCode(HttpStatus.NOT_FOUND.value())
                                .errors(List.of(enfe.getMessage()))
                                .errorDate(LocalDateTime.now())
                                .build());

    }

}
