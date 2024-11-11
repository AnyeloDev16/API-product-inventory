package com.skydev.product_inventory_management.presentation.advice;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.skydev.product_inventory_management.presentation.advice.response.ErrorResponse;
import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
public class ValidationExceptionHandler {

    @Value("${error.title.invalid_input}")
    private String errorInvalidInput;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> getMethodArgumentNotValidException(MethodArgumentNotValidException manve){

        return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse
                                .builder()
                                .title(errorInvalidInput)
                                .errorCode(HttpStatus.BAD_REQUEST.value())
                                .errors(manve.getBindingResult()
                                                .getFieldErrors()
                                                .stream()
                                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                                .toList())
                                .errorDate(LocalDateTime.now())
                                .build());

    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> getHandlerMethodValidationException(HandlerMethodValidationException hmve){

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse
                        .builder()
                        .title(errorInvalidInput)
                        .errorCode(HttpStatus.BAD_REQUEST.value())
                        .errors(hmve.getAllErrors().stream()
                                .map(MessageSourceResolvable::getDefaultMessage)
                                .toList())
                        .errorDate(LocalDateTime.now())
                        .build());

    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> getInvalidInputException(InvalidInputException iie){

        return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse
                                .builder()
                                .title(errorInvalidInput)
                                .errorCode(HttpStatus.NOT_FOUND.value())
                                .errors(iie.getListError())
                                .errorDate(LocalDateTime.now())
                                .build());

    }

}
