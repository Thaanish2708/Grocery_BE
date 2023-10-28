package com.grocery.service.Impl;

import com.grocery.entity.Category;
import com.grocery.payload.CategoryInputDto;
import com.grocery.repository.CategoryRepository;
import com.grocery.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryInputDto createCategory(CategoryInputDto categoryInputDto) {
        Category category = modelMapper.map(categoryInputDto,Category.class);
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryInputDto.class);
    }

    @Override
    public List<String> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(Category::getName).collect(Collectors.toList());
    }
}
