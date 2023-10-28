package com.grocery.controller;

import com.grocery.entity.Cart;
import com.grocery.entity.CartItems;
import com.grocery.entity.Product;
import com.grocery.payload.CartDto;
import com.grocery.payload.CartItemDto;
import com.grocery.payload.ProductCartDto;
import com.grocery.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/users")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("{userId}/cart")
    private ResponseEntity<CartDto> createCart(@PathVariable(name = "userId") Long userId,
                                            @RequestBody ProductCartDto productCartDto){
        return new ResponseEntity<>(cartService.addToCart(userId,productCartDto), HttpStatus.CREATED);
    }

    @GetMapping("{userId}/cart")
    private CartDto getCart(@PathVariable(name = "userId") Long userId){
        return cartService.getCartItems(userId);
    }

}
