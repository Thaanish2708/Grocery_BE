package com.grocery.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInputDto {
    private Long id;
    private String name;
    private String imageUrl;
    private Long availableQty;
    private String portionSize;
    private Long timesPurchased;
    private boolean availability;
    private Long categoryId;
    private double price;
}
