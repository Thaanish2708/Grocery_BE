package com.grocery.controller;

import com.grocery.payload.CartDto;
import com.grocery.payload.ProductCartDto;
import com.grocery.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/users")
@Tag(name = "Cart Controller", description = "Operations pertaining to cart entities")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("{userId}/cart")
    @Operation(summary = "Create a Cart with user id", description = "Endpoint to create a cart with user id")
    private ResponseEntity<CartDto> createCart(@PathVariable(name = "userId") Long userId,
                                            @RequestBody ProductCartDto productCartDto){
        return new ResponseEntity<>(cartService.addToCart(userId,productCartDto), HttpStatus.CREATED);
    }

    @GetMapping("{userId}/cart")
    @Operation(summary = "Get a Cart for a particular user", description = "Endpoint to get cart items for a particular user")
    private CartDto getCart(@PathVariable(name = "userId") Long userId){
        return cartService.getCartItems(userId);
    }

    @DeleteMapping("{userId}/cart/remove")
    private CartDto removeItemInCart(@PathVariable(name = "userId") Long userId,
                                             @RequestBody ProductCartDto productCartDto){
         return cartService.removeItemInCart(userId, productCartDto);
    }
}
