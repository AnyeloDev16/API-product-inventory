package com.skydev.product_inventory_management.presentation.advice;

import java.time.LocalDateTime;
import java.util.List;

import com.skydev.product_inventory_management.util.TitleMessageUtils;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ValidationExceptionHandler {

    private final TitleMessageUtils titleMessageUtils;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> getMethodArgumentNotValidException(MethodArgumentNotValidException manve){

        return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse
                                .builder()
                                .title(titleMessageUtils.INVALID_INPUT_PROVIDED)
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
                        .title(titleMessageUtils.INVALID_INPUT_PROVIDED)
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
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse
                                .builder()
                                .title(titleMessageUtils.INVALID_INPUT_PROVIDED)
                                .errorCode(HttpStatus.BAD_REQUEST.value())
                                .errors(List.of(iie.getMessage()))
                                .errorDate(LocalDateTime.now())
                                .build());

    }

}
