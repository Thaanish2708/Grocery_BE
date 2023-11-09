package com.grocery.controller;

import com.grocery.entity.Cart;
import com.grocery.entity.Order;
import com.grocery.payload.CartDto;
import com.grocery.payload.OrderDto;
import com.grocery.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller", description = "Operations pertaining to order entities")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @PostMapping("/{userId}")
    @Operation(summary = "Create an order with existing cart", description = "Endpoint to create a order")
    private OrderDto createOrder(@PathVariable(name = "userId") Long userId,
                                 @RequestBody CartDto cart){
        return orderService.createOrder(userId,cart);
    }

    @PatchMapping("/{orderId}")
    @Operation(summary = "Update Order Status", description = "Endpoint to update order status")
    private String updateOrderStatus(@PathVariable(name = "orderId") Long orderId){
        return orderService.updateOrderStatus(orderId);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get successful orders of a user", description = "Endpoint to get orders in success state")
    private List<OrderDto> getAllSuccessfulOrders(@PathVariable(name = "userId") Long userId){
        return orderService.getSuccessOrders(userId);
    }
}
