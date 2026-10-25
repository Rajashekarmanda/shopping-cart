package com.codewithraj.shoppingcart.repository;

import com.codewithraj.shoppingcart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> getAllCategories();
}
