package com.skydev.product_inventory_management.presentation.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.skydev.product_inventory_management.persistence.entity.enums.OrderStatus;
import com.skydev.product_inventory_management.presentation.dto.request.order.RegisterOrderDTO;
import com.skydev.product_inventory_management.presentation.dto.request.order.UpdateOrderStatusDTO;
import com.skydev.product_inventory_management.presentation.dto.response.order.ResponseOrderBuyDTO;
import com.skydev.product_inventory_management.presentation.dto.response.order.ResponseOrderDTO;
import com.skydev.product_inventory_management.presentation.dto.response.order.ResponseOrderDetailDTO;
import com.skydev.product_inventory_management.presentation.dto.response.order.ResponseOrderWithOrderDetailsDTO;
import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import com.skydev.product_inventory_management.service.interfaces.IOrderService;
import com.skydev.product_inventory_management.util.JwtUtils;
import com.skydev.product_inventory_management.util.MessageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;
    private final MessageUtils messageUtils;
    private final JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<ResponseOrderBuyDTO> buyOrder(@Valid @RequestBody RegisterOrderDTO registerOrderDTO, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader) {

        authHeader = authHeader.substring(7);

        DecodedJWT decodedJWT = jwtUtils.validateToken(authHeader);

        Long idUser = decodedJWT.getClaim("idUser").asLong();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.buyOrder(registerOrderDTO, idUser));

    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {

        if(orderId < 0){
            throw new InvalidInputException(messageUtils.ORDER_ID_INVALID);
        }

        orderService.cancelOrder(orderId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseOrderWithOrderDetailsDTO> getOrder(@PathVariable Long orderId) {

        if(orderId < 0){
            throw new InvalidInputException(messageUtils.ORDER_ID_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getOrderById(orderId));

    }

    @PutMapping("/changeStatus/{orderId}")
    public ResponseEntity<Void> changeOrderStatus(@PathVariable Long orderId, @Valid @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO) {

        if(orderId < 0){
            throw new InvalidInputException(messageUtils.ORDER_ID_INVALID);
        }

        orderService.changeOrderStatus(orderId, OrderStatus.convertToOrderStatus(updateOrderStatusDTO.getOrderStatus()));

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();

    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<List<ResponseOrderDetailDTO>> getAllProductsByOder(@PathVariable Long orderId) {

        if(orderId < 0){
            throw new InvalidInputException(messageUtils.ORDER_ID_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getAllProductsByOder(orderId));

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ResponseOrderDTO>> getAllOrdersByUser(@PathVariable Long userId, @RequestParam Integer pageSize, @RequestParam Integer pageNumber) {

        if(userId < 0){
            throw new InvalidInputException(messageUtils.USER_ID_INVALID);
        }

        if(pageSize < 0 || pageNumber < 0){
            throw new InvalidInputException(messageUtils.PAGE_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getAllOrdersByUser(userId, pageNumber, pageSize));

    }

    @GetMapping
    public ResponseEntity<Page<ResponseOrderDTO>> getAllOrders(@RequestParam Integer pageSize, @RequestParam Integer pageNumber) {

        if(pageSize < 0 || pageNumber < 0){
            throw new InvalidInputException(messageUtils.PAGE_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orderService.getAllOrders(pageNumber, pageSize));

    }

}
