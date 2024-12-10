package com.skydev.product_inventory_management.service.interfaces;

import com.skydev.product_inventory_management.presentation.dto.request.category.RegisterCategoryDTO;
import com.skydev.product_inventory_management.presentation.dto.request.category.UpdateCategoryDTO;
import com.skydev.product_inventory_management.presentation.dto.response.category.ResponseCategoryDTO;

import java.util.List;

public interface ICategoryService {

    ResponseCategoryDTO saveCategory(RegisterCategoryDTO registerCategoryDTO);
    ResponseCategoryDTO updateCategory(Long idCategory, UpdateCategoryDTO updateCategoryDTO);
    ResponseCategoryDTO findCategoryById(Long idCategory);
    List<ResponseCategoryDTO> findCategoryAll();

}
