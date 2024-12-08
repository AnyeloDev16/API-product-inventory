package com.skydev.product_inventory_management.presentation.dto.request.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterOrderDTO {

    @NotNull(message = "Address is required")
    @Positive(message = "Address is invalid")
    private Long addressId;

    @Valid
    @NotNull(message = "Order details is required")
    @NotEmpty(message = "Min one order detail")
    private List<RegisterOrderDetailDTO> orderDetails;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.01", message = "Total amount must be greater than 0.01")
    @DecimalMax(value = "99999999.99", message = "Total amount must be less than 99999999.99")
    private BigDecimal totalAmount;

    @NotNull(message = "Payment method is required")
    @Positive(message = "Payment method is invalid")
    private Long paymentMethodId;

}
