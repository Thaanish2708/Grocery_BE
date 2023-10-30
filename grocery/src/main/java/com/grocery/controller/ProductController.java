package com.grocery.controller;

import com.grocery.payload.ProductInputDto;
import com.grocery.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "Operations pertaining to product entities")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Operation(summary = "Create a Product", description = "Endpoint to create a product")
    private ResponseEntity<ProductInputDto> createProduct(@RequestBody ProductInputDto
                                                                      productInputDto){
        return new ResponseEntity<>(productService.createProduct(productInputDto),
                HttpStatus.CREATED);
    }

    @GetMapping("{productId}")
    @Operation(summary = "Get a Product with product ID", description = "Endpoint to get a product")
    private ProductInputDto getProduct(@PathVariable(name = "productId") Long productId){
        return productService.getProductWithId(productId);
    }

    @GetMapping("/search")
    @Operation(summary = "Search product with name", description = "Endpoint to get a product")
    private List<ProductInputDto> searchProduct(@RequestParam("query") String query){
        return productService.searchProduct(query);
    }
}