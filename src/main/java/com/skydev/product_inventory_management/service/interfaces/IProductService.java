package com.skydev.product_inventory_management.service.interfaces;

import com.skydev.product_inventory_management.presentation.dto.relationDTO.IResponseProduct;
import com.skydev.product_inventory_management.presentation.dto.request.product.RegisterProductDTO;
import com.skydev.product_inventory_management.presentation.dto.request.product.UpdateProductDTO;
import com.skydev.product_inventory_management.presentation.dto.response.product.ResponseProductAdminDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface IProductService{

    ResponseProductAdminDTO saveProduct(RegisterProductDTO registerProductDTO, Long idUser);
    ResponseProductAdminDTO updateProduct(Long idProduct, UpdateProductDTO updateProductDTO, Long idUser);
    void updateActive(Long idProduct, Long idUser);
    <T extends IResponseProduct> Page<T> findProductAll(Class<T> dtoClass, int pageNumber, int pageSize, Boolean active);
    <T extends IResponseProduct> Page<T> findProductAllByProductName(Class<T> dtoClass, String productName, int pageNumber, int pageSize, Boolean active);
    <T extends IResponseProduct> Page<T> findProductAllByCategoryId(Class<T> dtoClass, Long idCategory, int pageNumber, int pageSize, Boolean active);
    <T extends IResponseProduct> Page<T> findProductAllBetweenPrice(Class<T> dtoClass, BigDecimal minPrice, BigDecimal maxPrice, int pageNumber, int pageSize, Boolean active);
    <T extends IResponseProduct> T findProductById(Long idProduct, Class<T> dtoClass);

}
