package com.grocery.service.Impl;

import com.grocery.entity.*;
import com.grocery.exception.ResourceNotFoundException;
import com.grocery.payload.CartDto;
import com.grocery.payload.CartItemDto;
import com.grocery.payload.OrderDto;
import com.grocery.repository.OrderRepository;
import com.grocery.repository.UserRepository;
import com.grocery.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(Long userId, CartDto cart) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found with id: "+userId));
        Long cartId = cart.getId();
        Order order = new Order();
        List<CartItems> cartItemsList = cart.getCartItems().stream()
                .map(cartItemDto -> modelMapper.map(cartItemDto, CartItems.class))
                .toList();;
        order.setOrderItems(convertToOrderItemList(cartItemsList,order));
        order.setUser(user);
        order.setTotalValue(cart.getTotalValue());
        order.setOrderItemsCount(cart.getCartItemsCount());
        order.setStatus("PENDING");
        OrderDto orderDto = modelMapper.map(order,OrderDto.class);
        orderRepository.save(order);
        return orderDto;
    }

    @Override
    public String updateOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->
                new ResourceNotFoundException("Order not found with id: "+orderId));
        order.setStatus("SUCCESS");
        orderRepository.save(order);
        return "Order Successful";
    }

    @Override
    public List<OrderDto> getSuccessOrders(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User ID not found"));
        List<Order> successOrders = orderRepository.findByStatus("SUCCESS", String.valueOf(userId));

        return successOrders.stream().map(this::convertToOrderDto).collect(Collectors.toList());
    }

    private OrderDto convertToOrderDto(Order order) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(order, OrderDto.class);
    }

    List<OrderItems> convertToOrderItemList(List<CartItems> cartItemsList, Order order){
        List<OrderItems> orderItemList = new ArrayList<>();
        for(CartItems cartItems:cartItemsList){
            OrderItems orderItem = new OrderItems();
            orderItem.setProduct(cartItems.getProduct());
            orderItem.setQuantity(cartItems.getQuantity());
            orderItem.setOrder(order);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }



}
