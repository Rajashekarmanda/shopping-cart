package com.codewithraj.shoppingcart.service;

import com.codewithraj.shoppingcart.model.Category;
import com.codewithraj.shoppingcart.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories =categoryRepository.findAll();
        return categories.size() > 0 ? categories : null;
    }
}
