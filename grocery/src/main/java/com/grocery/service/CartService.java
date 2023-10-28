package com.grocery.service;

import com.grocery.entity.Cart;
import com.grocery.entity.CartItems;
import com.grocery.payload.CartDto;
import com.grocery.payload.CartItemDto;
import com.grocery.payload.ProductCartDto;

import java.util.List;

public interface CartService {
    CartDto addToCart(Long userId, ProductCartDto productCartDto);

//    List<CartItemDto> getCartItems(Long userId);
    CartDto getCartItems(Long userId);
}
