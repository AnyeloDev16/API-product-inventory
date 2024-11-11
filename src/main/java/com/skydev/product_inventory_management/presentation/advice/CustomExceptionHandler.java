package com.skydev.product_inventory_management.presentation.advice;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.skydev.product_inventory_management.presentation.advice.response.ErrorResponse;
import com.skydev.product_inventory_management.service.exceptions.EntityNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler {

    @Value("${error.title.not_found}")
    private String errorEntityNotFound;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> getEntityNotFoundException(EntityNotFoundException enfe){

        return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse
                                .builder()
                                .title(errorEntityNotFound)
                                .errorCode(HttpStatus.NOT_FOUND.value())
                                .errors(enfe.getListError())
                                .errorDate(LocalDateTime.now())
                                .build());

    }

}
