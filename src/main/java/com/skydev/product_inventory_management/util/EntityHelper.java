package com.skydev.product_inventory_management.util;

import com.skydev.product_inventory_management.persistence.entity.Category;
import com.skydev.product_inventory_management.persistence.entity.Product;
import com.skydev.product_inventory_management.persistence.entity.UserEntity;
import com.skydev.product_inventory_management.persistence.entity.enums.Gender;
import com.skydev.product_inventory_management.presentation.dto.request.category.UpdateCategoryDTO;
import com.skydev.product_inventory_management.presentation.dto.request.product.UpdateProductDTO;
import com.skydev.product_inventory_management.presentation.dto.request.user.UpdateUserDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EntityHelper {

    private EntityHelper() {
        throw new IllegalStateException("Utility class");
    }
    
    public static void updateUser(UserEntity user, UpdateUserDTO updateUser){

        String name = updateUser.getName();
        String lastFirstName = updateUser.getFirstLastName();
        String lastSecondName = updateUser.getSecondLastName();
        String gender = updateUser.getGender();
        LocalDate birthDate = updateUser.getBirthDate();
        String phone = updateUser.getPhone();

        if(name != null){
            user.setName(name);
        }

        if(lastFirstName != null){
            user.setFirstLastName(lastFirstName);
        }

        if(lastSecondName != null){
            user.setSecondLastName(lastSecondName);
        }

        if(gender != null){
            user.setGender(Gender.convertToGender(gender));
        }

        if(birthDate != null){
            user.setBirthDate(birthDate);
        }

        if(phone != null){
            user.setPhone(phone);
        }

    }

    public static void updateCategory(Category category, UpdateCategoryDTO updateCategory){

        String categoryName = updateCategory.getCategoryName();

        if(categoryName != null){
            category.setCategoryName(categoryName);
        }

    }

    public static void updateProduct(Product product, UpdateProductDTO updateProductDTO, Category category){

        String productName = updateProductDTO.getProductName();
        String description = updateProductDTO.getDescription();
        BigDecimal purchasePrice = updateProductDTO.getPurchasePrice();
        BigDecimal salePrice = updateProductDTO.getSalePrice();
        Integer stock = updateProductDTO.getStock();
        String productPath = updateProductDTO.getProductPath();

        if(category != null){
            product.setCategory(category);
        }

        if(productName != null){
            product.setProductName(productName);
        }

        if(description != null){
            product.setDescription(description);
        }

        if(purchasePrice != null){
            product.setPurchasePrice(purchasePrice);
        }

        if(salePrice != null){
            product.setSalePrice(salePrice);
        }

        if(stock != null){
            product.setStock(stock);
        }

        if(productPath != null){
            product.setImagePath(productPath);
        }

    }

}
