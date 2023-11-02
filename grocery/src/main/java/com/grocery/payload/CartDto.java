package com.grocery.payload;


import com.grocery.entity.CartItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CartDto {
//    private Long id;
    private List<CartItemDto> cartItems;
    private double totalValue;
    private Integer cartItemsCount;
}
