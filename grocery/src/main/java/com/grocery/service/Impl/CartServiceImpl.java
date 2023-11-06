package com.grocery.service.Impl;

import com.grocery.entity.Cart;
import com.grocery.entity.CartItems;
import com.grocery.entity.Product;
import com.grocery.entity.User;
import com.grocery.exception.ResourceNotFoundException;
import com.grocery.payload.CartDto;
import com.grocery.payload.CartItemDto;
import com.grocery.payload.ProductCartDto;
import com.grocery.repository.CartItemRepository;
import com.grocery.repository.CartRepository;
import com.grocery.repository.ProductRepository;
import com.grocery.repository.UserRepository;
import com.grocery.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto addToCart(Long userId, ProductCartDto productCartDto) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found with id "+ userId));
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
            cart = cartRepository.save(cart);
        }
        List<CartItems> cartItems = cart.getCartItems();
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        for (CartItems item : cartItems) {
            if (item.getProduct().getId().equals(productCartDto.getProductId())) {
                item.setQuantity(item.getQuantity() + productCartDto.getQuantity());
                if(item.getQuantity()==0){
                    cartItems.remove(item);
                }
                Product product = productRepository.findById(productCartDto.getProductId()).orElseThrow(()->
                        new ResourceNotFoundException("Product not found with id"+productCartDto.getProductId()));
                cart.setTotalValue(cart.getTotalValue() + product.getPrice() * productCartDto.getQuantity());
                cart.setCartItemsCount((cart.getCartItemsCount()!=null? cart.getCartItemsCount():0)+productCartDto.getQuantity());
                cartRepository.save(cart);
                cartItemRepository.save(item);
                return modelMapper.map(cart,CartDto.class);
            }
        }
        Product product = productRepository.findById(productCartDto.getProductId()).orElseThrow(()->
                new ResourceNotFoundException("Product not found with id "+productCartDto.getProductId()));
        CartItems cartItem = new CartItems();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(productCartDto.getQuantity());
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        cart.setTotalValue(cart.getTotalValue()+ product.getPrice() * productCartDto.getQuantity());
        cart.setCartItemsCount((cart.getCartItemsCount()!=null? cart.getCartItemsCount():0)+productCartDto.getQuantity());
//        cart.setCartItemsCount(100);
        cartItemRepository.save(cartItem);
        userRepository.save(user);
        return modelMapper.map(cart,CartDto.class);
    }

    @Override
    public CartDto getCartItems(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found with id "+ userId));
        CartDto cartDto = new CartDto();
        List<Cart> carts = cartRepository.findByUserId(userId);
        if (!carts.isEmpty()) {
            Cart cart = carts.get(0);
            cartDto.setCartItems(convertToDto(cart.getCartItems()));
            cartDto.setTotalValue(cart.getTotalValue());
            cartDto.setCartItemsCount(cart.getCartItemsCount());
            return cartDto;
        } else {
            return cartDto;
        }
    }

    private List<CartItemDto> convertToDto(List<CartItems> cartItems) {
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for (CartItems cartItem : cartItems) {
            cartItemDtos.add(modelMapper.map(cartItem, CartItemDto.class));
        }
        return cartItemDtos;
    }
}
