package com.grocery.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderDto {
    private Long id;
    private List<OrderItemDto> orderItems;
    private double totalValue;
    private String status;
    private Integer orderItemsCount;
}
