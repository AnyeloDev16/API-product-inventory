package com.skydev.product_inventory_management.persistence.repository;

import com.skydev.product_inventory_management.persistence.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCart_CartIdAndProduct_ProductId(Long cartId, Long productId);
    List<CartItem> findAllByCart_CartId(Long cartId);
    void deleteByCart_CartIdAndProduct_ProductId(Long cartId, Long productId);
    void deleteAllByCart_CartId(Long cartId);

}
