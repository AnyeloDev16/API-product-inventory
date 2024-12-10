package com.skydev.product_inventory_management.service.interfaces;

import com.skydev.product_inventory_management.presentation.dto.request.cart.AddCartItemDTO;
import com.skydev.product_inventory_management.presentation.dto.response.cart.ResponseCartDTO;
import com.skydev.product_inventory_management.presentation.dto.response.cart.ResponseCartItemDTO;

import java.util.List;

public interface ICartService {

    ResponseCartDTO findCartByUserId(Long userId);

    void createCart(Long userId);
    void addCartItem(Long userId, AddCartItemDTO addCartItemDTO);
    void deleteCartItem(Long userId, Long productId);
    void deleteAllCartItems(Long userId);

    ResponseCartItemDTO getCartItem(Long cartId, Long productId);
    List<ResponseCartItemDTO> getAllCartItemsByCartId(Long cartId);

}
