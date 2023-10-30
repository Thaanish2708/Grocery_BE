package com.grocery.controller;

import com.grocery.payload.CategoryInputDto;
import com.grocery.payload.ProductInputDto;
import com.grocery.payload.ProductOutputDto;
import com.grocery.service.CategoryService;
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
@RequestMapping("/category")
@Tag(name = "Category Controller", description = "Operations pertaining to category entities")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    //http://localhost:8080/category
    @PostMapping
    @Operation(summary = "Create a Category", description = "Endpoint to create a category")
    private ResponseEntity<CategoryInputDto> createCategory(@RequestBody  CategoryInputDto
                                            categoryInputDto){
        return new ResponseEntity<>(categoryService.createCategory(categoryInputDto),
                HttpStatus.CREATED);
    }

    //http://localhost:8080/category/all
    @GetMapping("/all")
    @Operation(summary = "Get list of all category names", description = "Endpoint to get all category names")
    private List<String> getAllCategories(){
        return categoryService.getAllCategories();
    }

    //http://localhost:8080/category/{categoryId}
    @GetMapping("{categoryId}")
    @Operation(summary = "Get list of products with category id", description = "Endpoint to get list of products with category id")
    private List<ProductInputDto> getCategoryProducts(@PathVariable(name = "categoryId")
                                                      Long categoryId){
        return productService.getProducts(categoryId);
    }

    //http://localhost:8080/category/name/?categoryName={categoryName}
    @GetMapping("/name")
    @Operation(summary = "Get list of products with category name", description = "Endpoint to get list of products with category name")
    private List<ProductInputDto> getCategoryProductsName(@RequestParam("categoryName")
                                                          String categoryName){
        return productService.getProductsWithCategoryName(categoryName);
    }

}
