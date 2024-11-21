package com.skydev.product_inventory_management.presentation.controller;

import com.skydev.product_inventory_management.presentation.dto.request.category.RegisterCategoryDTO;
import com.skydev.product_inventory_management.presentation.dto.request.category.UpdateCategoryDTO;
import com.skydev.product_inventory_management.presentation.dto.response.category.ResponseCategoryDTO;
import com.skydev.product_inventory_management.service.exceptions.InvalidInputException;
import com.skydev.product_inventory_management.service.interfaces.ICategoryService;
import com.skydev.product_inventory_management.util.MessageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;
    private final MessageUtils messageUtils;

    @PostMapping
    public ResponseEntity<ResponseCategoryDTO> saveCategory(@Valid @RequestBody RegisterCategoryDTO registerCategoryDTO){

        return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(categoryService.saveCategory(registerCategoryDTO));

    }

    @PutMapping("/{idCategory}")
    public ResponseEntity<ResponseCategoryDTO> updateCategory(@PathVariable Long idCategory, @Valid @RequestBody UpdateCategoryDTO updateCategoryDTO){

        if(idCategory <= 0){
            throw new InvalidInputException(messageUtils.CATEGORY_ID_INVALID);
        }

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(categoryService.updateCategory(idCategory, updateCategoryDTO));

    }

    @GetMapping("/{idCategory}")
    public ResponseEntity<ResponseCategoryDTO> findByIdCategory(@PathVariable Long idCategory){

        if(idCategory <= 0){
            throw new InvalidInputException(messageUtils.CATEGORY_ID_INVALID);
        }

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(categoryService.findCategoryById(idCategory));

    }

    @GetMapping
    public ResponseEntity<List<ResponseCategoryDTO>> findAllCategory(){

        return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(categoryService.findCategoryAll());

    }

}
