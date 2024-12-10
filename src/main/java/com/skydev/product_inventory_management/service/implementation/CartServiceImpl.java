package com.skydev.product_inventory_management.service.implementation;

import com.skydev.product_inventory_management.persistence.entity.Cart;
import com.skydev.product_inventory_management.persistence.entity.CartItem;
import com.skydev.product_inventory_management.persistence.entity.Product;
import com.skydev.product_inventory_management.persistence.entity.UserEntity;
import com.skydev.product_inventory_management.persistence.repository.ICartItemRepository;
import com.skydev.product_inventory_management.persistence.repository.ICartRepository;
import com.skydev.product_inventory_management.presentation.dto.request.cart.AddCartItemDTO;
import com.skydev.product_inventory_management.presentation.dto.response.cart.ResponseCartDTO;
import com.skydev.product_inventory_management.presentation.dto.response.cart.ResponseCartItemDTO;
import com.skydev.product_inventory_management.service.exceptions.EntityNotFoundException;
import com.skydev.product_inventory_management.service.interfaces.ICartService;
import com.skydev.product_inventory_management.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {

    private final ICartRepository cartRepository;
    private final ICartItemRepository cartItemRepository;
    private final MessageUtils messageUtils;

    @Override
    @Transactional(readOnly = true)
    public ResponseCartDTO findCartByUserId(Long userId) {

        Cart cart =  cartRepository.findByUser_UserId(userId).orElseThrow(() -> new EntityNotFoundException(messageUtils.CART_ID_NOT_FOUND));

        return ResponseCartDTO.builder()
                .cartId(cart.getCartId())
                .userId(cart.getUser().getUserId())
                .build();

    }

    @Override
    @Transactional
    public void createCart(Long userId) {
        Cart newCart = Cart.builder().user(UserEntity.builder().userId(userId).build()).build();
        cartRepository.save(newCart);
    }

    @Override
    @Transactional
    public void addCartItem(Long userId, AddCartItemDTO addCartItemDTO) {

        Cart cart = cartRepository.findByUser_UserId(userId).orElseThrow(() -> new EntityNotFoundException(messageUtils.CART_ID_NOT_FOUND));

        CartItem cartItem = CartItem.builder()
                .cart(Cart.builder().cartId(cart.getCartId()).build())
                .product(Product.builder().productId(addCartItemDTO.getProductId()).build())
                .quantity(addCartItemDTO.getQuantity())
                .build();

        cartItemRepository.save(cartItem);

    }

    @Override
    @Transactional
    public void deleteCartItem(Long userId, Long productId) {

        Cart cart = cartRepository.findByUser_UserId(userId).orElseThrow(() -> new EntityNotFoundException(messageUtils.CART_ID_NOT_FOUND));

        cartItemRepository.deleteByCart_CartIdAndProduct_ProductId(cart.getCartId(), productId);

    }

    @Override
    @Transactional
    public void deleteAllCartItems(Long userId) {

        Cart cart = cartRepository.findByUser_UserId(userId).orElseThrow(() -> new EntityNotFoundException(messageUtils.CART_ID_NOT_FOUND));
        cartItemRepository.deleteAllByCart_CartId(cart.getCartId());

    }

    @Override
    @Transactional(readOnly = true)
    public ResponseCartItemDTO getCartItem(Long cartId, Long productId) {
        CartItem cartItem = cartItemRepository.findByCart_CartIdAndProduct_ProductId(cartId, productId).orElseThrow(() -> new EntityNotFoundException(messageUtils.CART_ID_NOT_FOUND));

        Product product = cartItem.getProduct();

        return  ResponseCartItemDTO.builder()
                .cartId(cartId)
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getSalePrice())
                .quantity(cartItem.getQuantity())
                .build();

    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseCartItemDTO> getAllCartItemsByCartId(Long cartId) {
        return cartItemRepository.findAllByCart_CartId(cartId).stream()
                .map(ci -> {

                    Product product = ci.getProduct();

                    return ResponseCartItemDTO.builder()
                            .cartId(cartId)
                            .productId(product.getProductId())
                            .productName(product.getProductName())
                            .productPrice(product.getSalePrice())
                            .quantity(ci.getQuantity())
                            .build();

                })
                .toList();
    }
}
