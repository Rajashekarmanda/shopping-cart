package com.codewithraj.shoppingcart.controller;

import com.codewithraj.shoppingcart.model.Category;
import com.codewithraj.shoppingcart.response.ApiResponse;
import com.codewithraj.shoppingcart.response.ResourceNotFoundException;
import com.codewithraj.shoppingcart.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> getCategoryByName(@RequestParam String name) {
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found Category",category));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
        Category newCategory = categoryService.addCategory(category);
        if (newCategory != null) {
            return ResponseEntity.ok(new ApiResponse("Added Category",newCategory));
        }else  {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody Category category, @PathVariable Long id) {
        Optional<Category> categoryToUpdate = categoryService.getCategoryById(id);
        if (categoryToUpdate.isPresent()) {
            Category categoryUpdated =  categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("Updated Category",categoryUpdated));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        Optional<Category> categoryToDelete = categoryService.getCategoryById(id);
        if (categoryToDelete.isPresent()) {
            categoryService.deleteCategory(categoryToDelete.get().getId());
            return ResponseEntity.ok(new ApiResponse("Deleted Category",categoryToDelete));
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
