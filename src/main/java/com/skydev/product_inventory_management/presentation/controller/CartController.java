package com.skydev.product_inventory_management.presentation.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.skydev.product_inventory_management.presentation.dto.request.cart.AddCartItemDTO;
import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import com.skydev.product_inventory_management.service.interfaces.ICartService;
import com.skydev.product_inventory_management.util.JwtUtils;
import com.skydev.product_inventory_management.util.MessageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;
    private final JwtUtils jwtUtils;
    private final MessageUtils messageUtils;

    @PostMapping
    public ResponseEntity<Void> addCartItem(@Valid @RequestBody AddCartItemDTO addCartItemDTO, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader) {

        authHeader = authHeader.substring(7);

        DecodedJWT decodedJWT = jwtUtils.validateToken(authHeader);

        Long idUser = decodedJWT.getClaim("idUser").asLong();

        cartService.addCartItem(idUser, addCartItemDTO);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long productId, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String authHeader) {

        if(productId <= 0){
            throw new InvalidInputException(messageUtils.PRODUCT_ID_INVALID);
        }

        authHeader = authHeader.substring(7);

        DecodedJWT decodedJWT = jwtUtils.validateToken(authHeader);

        Long idUser = decodedJWT.getClaim("idUser").asLong();

        cartService.deleteCartItem(idUser, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();

    }

}
