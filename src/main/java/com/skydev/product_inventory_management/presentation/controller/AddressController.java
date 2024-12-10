package com.skydev.product_inventory_management.presentation.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.skydev.product_inventory_management.presentation.dto.request.address.RegisterAddressDTO;
import com.skydev.product_inventory_management.presentation.dto.request.address.UpdateAddressDTO;
import com.skydev.product_inventory_management.presentation.dto.response.address.ResponseAddressDTO;
import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import com.skydev.product_inventory_management.service.interfaces.IAddressService;
import com.skydev.product_inventory_management.util.JwtUtils;
import com.skydev.product_inventory_management.util.MessageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final IAddressService addressService;
    private final MessageUtils messageUtils;
    private final JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<ResponseAddressDTO> saveAddress(@Valid @RequestBody RegisterAddressDTO registerAddressDTO, @RequestHeader(value = "Authorization") String authHeader) {

        authHeader = authHeader.substring(7);

        DecodedJWT decodedJWT = jwtUtils.validateToken(authHeader);

        Long idUser = decodedJWT.getClaim("idUser").asLong();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressService.saveAddress(registerAddressDTO, idUser));
    }

    @PutMapping("/{idAddress}")
    public ResponseEntity<ResponseAddressDTO> updateAddress(@PathVariable Long idAddress, @Valid @RequestBody UpdateAddressDTO updateAddressDTO) {

        if(idAddress <= 0){
            throw new InvalidInputException(messageUtils.ADDRESS_ID_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressService.updateAddress(idAddress, updateAddressDTO));

    }

    @GetMapping("/{idAddress}")
    public ResponseEntity<ResponseAddressDTO> getAddress(@PathVariable Long idAddress) {

        if(idAddress <= 0){
            throw new InvalidInputException(messageUtils.ADDRESS_ID_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressService.getAddressById(idAddress));

    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<ResponseAddressDTO>> getAllAddressByUserId(@PathVariable Long idUser) {

        if(idUser <= 0){
            throw new InvalidInputException(messageUtils.USER_ID_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(addressService.getAllAddressByUserId(idUser));

    }

}
