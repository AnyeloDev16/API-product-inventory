package com.skydev.product_inventory_management.persistence.repository;

import com.skydev.product_inventory_management.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface IProductPagingRepository extends PagingAndSortingRepository<Product, Long> {

    Page<Product> findByProductNameContaining(String productName, Pageable pageable);
    Page<Product> findAllByCategory_CategoryId(Long categoryId, Pageable pageable);
    Page<Product> findAllBySalePriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
    Page<Product> findAllByActive(boolean active, Pageable pageable);
    Page<Product> findByProductNameContainingAndActive(String productName, boolean active, Pageable pageable);
    Page<Product> findAllByCategory_CategoryIdAndActive(Long categoryId, boolean active, Pageable pageable);
    Page<Product> findAllBySalePriceBetweenAndActive(BigDecimal minPrice, BigDecimal maxPrice, boolean active, Pageable pageable);

}
