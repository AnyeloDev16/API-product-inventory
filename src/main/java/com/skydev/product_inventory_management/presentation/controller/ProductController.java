package com.skydev.product_inventory_management.presentation.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.skydev.product_inventory_management.presentation.dto.request.product.RegisterProductDTO;
import com.skydev.product_inventory_management.presentation.dto.request.product.UpdateProductDTO;
import com.skydev.product_inventory_management.presentation.dto.response.product.ResponseProductAdminDTO;
import com.skydev.product_inventory_management.presentation.dto.response.product.ResponseProductUserDTO;
import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import com.skydev.product_inventory_management.service.interfaces.IProductService;
import com.skydev.product_inventory_management.util.JwtUtils;
import com.skydev.product_inventory_management.util.MessageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;
    private final MessageUtils messageUtils;
    private final JwtUtils jwtUtils;

    public static final boolean PRODUCT_ACTIVE = true;

    @PostMapping("/staff")
    public ResponseEntity<ResponseProductAdminDTO> saveProduct(@Valid @RequestBody RegisterProductDTO registerProductDTO, @RequestHeader(value = "Authorization") String authHeader){

        authHeader = authHeader.substring(7);

        DecodedJWT decodedJWT = jwtUtils.validateToken(authHeader);

        Long idUser = decodedJWT.getClaim("idUser").asLong();

        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(productService.saveProduct(registerProductDTO, idUser));
    }

    @PutMapping("/staff/{idProduct}")
    public ResponseEntity<ResponseProductAdminDTO> updateProduct(@PathVariable Long idProduct, @Valid @RequestBody UpdateProductDTO updateProductDTO, @RequestHeader(value = "Authorization") String authHeader){

        if(idProduct <= 0){
            throw new InvalidInputException(messageUtils.PRODUCT_ID_INVALID);
        }

        authHeader = authHeader.substring(7);

        DecodedJWT decodedJWT = jwtUtils.validateToken(authHeader);

        Long idUser = decodedJWT.getClaim("idUser").asLong();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.updateProduct(idProduct, updateProductDTO, idUser));
    }

    @PutMapping("/staff/{idProduct}/active")
    public ResponseEntity<Void> updateActive(@PathVariable Long idProduct, @RequestHeader(value = "Authorization") String authHeader){

        if(idProduct <= 0){
            throw new InvalidInputException(messageUtils.PRODUCT_ID_INVALID);
        }

        authHeader = authHeader.substring(7);

        DecodedJWT decodedJWT = jwtUtils.validateToken(authHeader);

        Long idUser = decodedJWT.getClaim("idUser").asLong();

        productService.updateActive(idProduct, idUser);

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();

    }

    @GetMapping("/staff/all")
    public ResponseEntity<Page<ResponseProductAdminDTO>> findProductAllStaff(@RequestParam Integer pageSize, @RequestParam Integer pageNumber, @RequestParam(required = false) Boolean active){

        if(pageSize < 0 || pageNumber < 0){
            throw new InvalidInputException(messageUtils.PAGE_INVALID);
        }

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(productService.findProductAll(ResponseProductAdminDTO.class, pageSize, pageNumber, active));

    }

    @GetMapping("/user/all")
    public ResponseEntity<Page<ResponseProductUserDTO>> findProductAll(@RequestParam Integer pageSize, @RequestParam Integer pageNumber){

        if(pageSize < 0 || pageNumber < 0){
            throw new InvalidInputException(messageUtils.PAGE_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findProductAll(ResponseProductUserDTO.class, pageSize, pageNumber, PRODUCT_ACTIVE));

    }

    @GetMapping("/staff/allByName")
    public ResponseEntity<Page<ResponseProductAdminDTO>> findProductAllByNameStaff(@RequestParam Integer pageSize, @RequestParam Integer pageNumber, @RequestParam String productName, @RequestParam(required = false) Boolean active){

        if(pageSize < 0 || pageNumber < 0){
            throw new InvalidInputException(messageUtils.PAGE_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findProductAllByProductName(ResponseProductAdminDTO.class, productName, pageSize, pageNumber, active));

    }

    @GetMapping("/user/allByName")
    public ResponseEntity<Page<ResponseProductUserDTO>> findProductAllByName(@RequestParam Integer pageSize, @RequestParam Integer pageNumber, @RequestParam String productName){

        if(pageSize < 0 || pageNumber < 0){
            throw new InvalidInputException(messageUtils.PAGE_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findProductAllByProductName(ResponseProductUserDTO.class, productName, pageSize, pageNumber, PRODUCT_ACTIVE));

    }

    @GetMapping("/staff/allByCategoryId")
    public ResponseEntity<Page<ResponseProductAdminDTO>> findProductAllByCategoryIdStaff(@RequestParam Integer pageSize, @RequestParam Integer pageNumber, @RequestParam Long categoryId, @RequestParam(required = false) Boolean active){

        if(pageSize < 0 || pageNumber < 0){
            throw new InvalidInputException(messageUtils.PAGE_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findProductAllByCategoryId(ResponseProductAdminDTO.class, categoryId, pageSize, pageNumber, active));

    }

    @GetMapping("/user/allByCategoryId")
    public ResponseEntity<Page<ResponseProductUserDTO>> findProductAllByCategoryId(@RequestParam Integer pageSize, @RequestParam Integer pageNumber, @RequestParam Long categoryId){

        if(pageSize < 0 || pageNumber < 0){
            throw new InvalidInputException(messageUtils.PAGE_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findProductAllByCategoryId(ResponseProductUserDTO.class, categoryId, pageSize, pageNumber, PRODUCT_ACTIVE));

    }

    @GetMapping("/staff/allBySalePrice")
    public ResponseEntity<Page<ResponseProductAdminDTO>> findProductAllBySalePriceStaff(@RequestParam Integer pageSize, @RequestParam Integer pageNumber, @RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice, @RequestParam(required = false) Boolean active){

        if(pageSize < 0 || pageNumber < 0){
            throw new InvalidInputException(messageUtils.PAGE_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findProductAllBetweenPrice(ResponseProductAdminDTO.class, minPrice, maxPrice, pageSize, pageNumber, active));

    }

    @GetMapping("/user/allBySalePrice")
    public ResponseEntity<Page<ResponseProductUserDTO>> findProductAllBySalePrice(@RequestParam Integer pageSize, @RequestParam Integer pageNumber, @RequestParam Double minPrice, @RequestParam Double maxPrice){

        if(pageSize < 0 || pageNumber < 0){
            throw new InvalidInputException(messageUtils.PAGE_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findProductAllBetweenPrice(ResponseProductUserDTO.class, BigDecimal.valueOf(minPrice), BigDecimal.valueOf(maxPrice), pageSize, pageNumber, PRODUCT_ACTIVE));

    }

    @GetMapping("/staff/{idProduct}")
    public ResponseEntity<ResponseProductAdminDTO> findByIdProduct(@PathVariable Long idProduct){

        if(idProduct <= 0){
            throw new InvalidInputException(messageUtils.PRODUCT_ID_INVALID);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findProductById(idProduct, ResponseProductAdminDTO.class));

    }

}
