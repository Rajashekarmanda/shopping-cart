package com.codewithraj.shoppingcart.service;

import com.codewithraj.shoppingcart.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> getAllCategories();

    Optional<Category> getCategoryById(Long id);

    Category getCategoryByName(String name);

    Category addCategory(Category category);

    Category updateCategory(Category category, Long id);

    void deleteCategory(Long id);
}
