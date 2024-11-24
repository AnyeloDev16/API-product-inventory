package com.skydev.product_inventory_management.service.implementation;

import com.skydev.product_inventory_management.persistence.entity.Category;
import com.skydev.product_inventory_management.persistence.repository.ICategoryRepository;
import com.skydev.product_inventory_management.presentation.dto.request.category.RegisterCategoryDTO;
import com.skydev.product_inventory_management.presentation.dto.request.category.UpdateCategoryDTO;
import com.skydev.product_inventory_management.presentation.dto.response.category.ResponseCategoryDTO;
import com.skydev.product_inventory_management.service.exceptions.EntityNotFoundException;
import com.skydev.product_inventory_management.service.interfaces.ICategoryService;
import com.skydev.product_inventory_management.util.EntityHelper;
import com.skydev.product_inventory_management.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final MessageUtils messageUtils;

    @Override
    public ResponseCategoryDTO saveCategory(RegisterCategoryDTO registerCategoryDTO) {

        Category category = modelMapper.map(registerCategoryDTO, Category.class);

        category = categoryRepository.save(category);

        return modelMapper.map(category, ResponseCategoryDTO.class);

    }

    @Override
    public ResponseCategoryDTO updateCategory(Long idCategory, UpdateCategoryDTO updateCategoryDTO) {

        Category findCategory = categoryRepository.findById(idCategory)
                                    .orElseThrow(() ->
                                            new EntityNotFoundException(messageUtils.CATEGORY_ID_NOT_FOUND));

        EntityHelper.updateCategory(findCategory, updateCategoryDTO);

        categoryRepository.save(findCategory);

        return modelMapper.map(findCategory, ResponseCategoryDTO.class);

    }

    @Override
    public ResponseCategoryDTO findCategoryById(Long idCategory) {

        Category findCategory = categoryRepository.findById(idCategory)
                                    .orElseThrow(() ->
                                            new EntityNotFoundException(messageUtils.CATEGORY_ID_NOT_FOUND));

        return modelMapper.map(findCategory, ResponseCategoryDTO.class);
    }

    @Override
    public List<ResponseCategoryDTO> findCategoryAll() {
        return categoryRepository.findAll()
                .stream()
                .map(cat -> modelMapper.map(cat, ResponseCategoryDTO.class))
                .toList();
    }
}
