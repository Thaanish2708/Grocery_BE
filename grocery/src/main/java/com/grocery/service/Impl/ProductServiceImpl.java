package com.grocery.service.Impl;

import com.grocery.entity.Category;
import com.grocery.entity.Product;
import com.grocery.exception.ResourceNotFoundException;
import com.grocery.payload.ProductInputDto;
import com.grocery.repository.CategoryRepository;
import com.grocery.repository.ProductRepository;
import com.grocery.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductInputDto createProduct(ProductInputDto productInputDto) {
        Product product = modelMapper.map(productInputDto, Product.class);
        productRepository.save(product);
        return modelMapper.map(product,ProductInputDto.class);
    }

    @Override
    public List<ProductInputDto> getProducts(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category not found with id "+categoryId));
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductInputDto> getProductsWithCategoryName(String categoryName) {
        Category category = categoryRepository.findByNameContainingIgnoreCase(categoryName);
        return getProducts(category.getId());
    }

    @Override
    public List<ProductInputDto> searchProduct(String query) {
        List<Product> searchResult = productRepository.findByNameContainingIgnoreCase(query);
        if(searchResult==null){
            return new ArrayList<>();
        }
        return searchResult.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ProductInputDto getProductWithId(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->
                new ResourceNotFoundException("Product not found with id "+ productId));
        return modelMapper.map(product,ProductInputDto.class);
    }

    private ProductInputDto convertToDTO(Product product) {
        ProductInputDto productDTO = new ProductInputDto();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setImageUrl(product.getImageUrl());
        productDTO.setAvailability(product.isAvailability());
        productDTO.setAvailableQty(product.getAvailableQty());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setPortionSize(product.getPortionSize());
        return productDTO;
    }
}
