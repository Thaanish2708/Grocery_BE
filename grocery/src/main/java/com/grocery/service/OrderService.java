package com.grocery.service;

import com.grocery.entity.Cart;
import com.grocery.payload.CartDto;
import com.grocery.payload.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(Long userId, CartDto cart);

    String updateOrderStatus(Long orderId);

    List<OrderDto> getSuccessOrders(Long userId);
}
