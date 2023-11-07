package com.grocery.service;

import com.grocery.entity.Cart;
import com.grocery.entity.CartItems;
import com.grocery.payload.CartDto;
import com.grocery.payload.CartItemDto;
import com.grocery.payload.ProductCartDto;

import java.util.List;

public interface CartService {
    CartDto addToCart(Long userId, ProductCartDto productCartDto);

    CartDto getCartItems(Long userId);

    CartDto removeItemInCart(Long userId,ProductCartDto productCartDto);
}
