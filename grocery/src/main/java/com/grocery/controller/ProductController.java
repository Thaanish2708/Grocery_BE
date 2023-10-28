package com.grocery.controller;

import com.grocery.payload.ProductInputDto;
import com.grocery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    private ResponseEntity<ProductInputDto> createProduct(@RequestBody ProductInputDto
                                                                      productInputDto){
        return new ResponseEntity<>(productService.createProduct(productInputDto),
                HttpStatus.CREATED);
    }

    @GetMapping("{productId}")
    private ProductInputDto getProduct(@PathVariable(name = "productId") Long productId){
        return productService.getProductWithId(productId);
    }
}