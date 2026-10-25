package com.codewithraj.shoppingcart.repository;

import com.codewithraj.shoppingcart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAll();

    Category findByName(String name);

    Optional<Category> findById(Long id);
}
