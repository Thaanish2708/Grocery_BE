package com.grocery.service.Impl;

import com.grocery.entity.*;
import com.grocery.exception.ResourceNotFoundException;
import com.grocery.payload.CartDto;
import com.grocery.payload.CartItemDto;
import com.grocery.payload.OrderDto;
import com.grocery.repository.CartRepository;
import com.grocery.repository.OrderRepository;
import com.grocery.repository.UserRepository;
import com.grocery.service.OrderService;
import jakarta.transaction.Transactional;
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

    @Autowired
    private CartRepository cartRepository;
    @Override
//    @Transactional
    public OrderDto createOrder(Long userId, CartDto cart) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("User not found with id: "+userId));
        Long cartId = cart.getId();
        Order order = new Order();
        Long orderId = order.getId();
        System.out.println("Order Id: "+order.getId());
        System.out.println("Order stat: "+order.getStatus());
        List<CartItems> cartItemsList = cart.getCartItems().stream()
                .map(cartItemDto -> modelMapper.map(cartItemDto, CartItems.class))
                .toList();;
        order.setOrderItems(convertToOrderItemList(cartItemsList,order));
        order.setUser(user);
        order.setTotalValue(cart.getTotalValue());
        order.setOrderItemsCount(cart.getCartItemsCount());
        order.setStatus("PENDING");
//        OrderDto orderDto;

        OrderDto orderDto = modelMapper.map(order,OrderDto.class);
//        orderDto.setId(orderId);
        System.out.println(orderDto.getId());
        orderRepository.save(order);
        cartRepository.deleteById(cartId);
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
        List<Order> successOrders = orderRepository.findByStatus( String.valueOf(userId));

        return successOrders.stream().map(this::convertToOrderDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->
                new ResourceNotFoundException("Order Not found with id: "+orderId));
        return modelMapper.map(order,OrderDto.class);
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
