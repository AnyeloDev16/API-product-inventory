package com.skydev.product_inventory_management.presentation.advice.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private String title;
    private int errorCode;
    private List<String> errors;
    private LocalDateTime errorDate;

}
