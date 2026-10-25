package com.codewithraj.shoppingcart.controller;

import com.codewithraj.shoppingcart.model.Category;
import com.codewithraj.shoppingcart.response.ApiResponse;
import com.codewithraj.shoppingcart.response.ResourceNotFoundException;
import com.codewithraj.shoppingcart.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse> getCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse("Fetched All Categories List",categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            Optional<Category> category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found Category",category));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found Category",category));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
