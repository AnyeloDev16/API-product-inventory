package com.skydev.product_inventory_management.util;

import com.skydev.product_inventory_management.persistence.entity.Address;
import com.skydev.product_inventory_management.persistence.entity.Category;
import com.skydev.product_inventory_management.persistence.entity.Product;
import com.skydev.product_inventory_management.persistence.entity.UserEntity;
import com.skydev.product_inventory_management.persistence.entity.enums.Gender;
import com.skydev.product_inventory_management.presentation.dto.request.address.UpdateAddressDTO;
import com.skydev.product_inventory_management.presentation.dto.request.category.UpdateCategoryDTO;
import com.skydev.product_inventory_management.presentation.dto.request.product.UpdateProductDTO;
import com.skydev.product_inventory_management.presentation.dto.request.user.UpdateUserDTO;
import org.modelmapper.internal.Pair;

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

    public static void updateAddress(Address address, UpdateAddressDTO updateAddress){

        String addressLine = updateAddress.getAddressLine();
        String zipCode = updateAddress.getZipCode();
        String street = updateAddress.getStreet();
        String district = updateAddress.getDistrict();
        String department = updateAddress.getDepartment();
        String country = updateAddress.getCountry();

        if(addressLine != null){
            address.setAddressLine(addressLine);
        }

        if(zipCode != null){
            address.setZipCode(zipCode);
        }

        if(street != null){
            address.setStreet(street);
        }

        if(district != null){
            address.setDistrict(district);
        }

        if(department != null){
            address.setDepartment(department);
        }

        if(country != null){
            address.setCountry(country);
        }

    }

    public static Pair<String, String> updateProduct(Product product, UpdateProductDTO updateProductDTO, Category category) {
        String productName = updateProductDTO.getProductName();
        String description = updateProductDTO.getDescription();
        BigDecimal purchasePrice = updateProductDTO.getPurchasePrice();
        BigDecimal salePrice = updateProductDTO.getSalePrice();
        Integer stock = updateProductDTO.getStock();
        String productPath = updateProductDTO.getProductPath();

        StringBuilder oldData = new StringBuilder("{");
        StringBuilder newData = new StringBuilder("{");

        if (category != null) {
            oldData.append("\"categoryId\": \"").append(product.getCategory().getCategoryId()).append("\", ");
            newData.append("\"categoryId\": \"").append(category.getCategoryId()).append("\", ");
            product.setCategory(category);
        }

        if (productName != null) {
            oldData.append("\"productName\": \"").append(product.getProductName()).append("\", ");
            newData.append("\"productName\": \"").append(productName).append("\", ");
            product.setProductName(productName);
        }

        if (description != null) {
            oldData.append("\"description\": \"").append(product.getDescription()).append("\", ");
            newData.append("\"description\": \"").append(description).append("\", ");
            product.setDescription(description);
        }

        if (purchasePrice != null) {
            oldData.append("\"purchasePrice\": ").append(product.getPurchasePrice()).append(", ");
            newData.append("\"purchasePrice\": ").append(purchasePrice).append(", ");
            product.setPurchasePrice(purchasePrice);
        }

        if (salePrice != null) {
            oldData.append("\"salePrice\": ").append(product.getSalePrice()).append(", ");
            newData.append("\"salePrice\": ").append(salePrice).append(", ");
            product.setSalePrice(salePrice);
        }

        if (stock != null) {
            oldData.append("\"stock\": ").append(product.getStock()).append(", ");
            newData.append("\"stock\": ").append(stock).append(", ");
            product.setStock(stock);
        }

        if (productPath != null) {
            oldData.append("\"productPath\": \"").append(product.getImagePath()).append("\", ");
            newData.append("\"productPath\": \"").append(productPath).append("\", ");
            product.setImagePath(productPath);
        }

        // Limpia las comas finales y cierra los JSON
        trimTrailingComma(oldData);
        trimTrailingComma(newData);

        oldData.append("}");
        newData.append("}");

        return Pair.of(oldData.toString(), newData.toString());
    }

    private static void trimTrailingComma(StringBuilder sb) {
        if (sb.length() > 2 && sb.charAt(sb.length() - 2) == ',') {
            sb.delete(sb.length() - 2, sb.length());
        }
    }

}
