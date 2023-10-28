package com.grocery.service;

import com.grocery.payload.CategoryInputDto;

import java.util.List;

public interface CategoryService {
    CategoryInputDto createCategory(CategoryInputDto categoryInputDto);

    List<String> getAllCategories();
}
