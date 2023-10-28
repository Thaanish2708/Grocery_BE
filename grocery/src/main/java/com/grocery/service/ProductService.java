package com.grocery.service;

import com.grocery.payload.ProductInputDto;

import java.util.List;

public interface ProductService {
    ProductInputDto createProduct(ProductInputDto productInputDto);

    List<ProductInputDto> getProducts(Long categoryId);

    ProductInputDto getProductWithId(Long productId);

    List<ProductInputDto> getProductsWithCategoryName(String categoryName);
}
