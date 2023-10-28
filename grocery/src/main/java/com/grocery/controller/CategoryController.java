package com.grocery.controller;

import com.grocery.payload.CategoryInputDto;
import com.grocery.payload.ProductInputDto;
import com.grocery.payload.ProductOutputDto;
import com.grocery.service.CategoryService;
import com.grocery.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    //http://localhost:8080/category
    @PostMapping
    private ResponseEntity<CategoryInputDto> createCategory(@RequestBody  CategoryInputDto
                                            categoryInputDto){
        return new ResponseEntity<>(categoryService.createCategory(categoryInputDto),
                HttpStatus.CREATED);
    }

    //http://localhost:8080/category/all
    @GetMapping("/all")
    private List<String> getAllCategories(){
        return categoryService.getAllCategories();
    }

    //http://localhost:8080/category/{categoryId}
    @GetMapping("{categoryId}")
    private List<ProductInputDto> getCategoryProducts(@PathVariable(name = "categoryId")
                                                      Long categoryId){
        return productService.getProducts(categoryId);
    }

    //http://localhost:8080/category/name/{categoryName}
    @GetMapping("name/{categoryName}")
    private List<ProductInputDto> getCategoryProductsName(@PathVariable(name = "categoryName")
                                                          String categoryName){
        System.out.println("Controller");
        return productService.getProductsWithCategoryName(categoryName);
    }

}
