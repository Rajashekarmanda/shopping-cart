package com.codewithraj.shoppingcart.repository;

import com.codewithraj.shoppingcart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE" +
            "(:name IS NULL OR p.name = :name) AND" +
            "(:brand IS NULL OR p.brand = :brand) AND" +
            "(:category IS NULL OR p.category.name = :category)")
    List<Product> findByFilters(@Param("name") String name,
                                @Param("brand") String brand,
                                @Param("category")  String category);

    Optional<Product> findByName(String name);

    long countByBrandAndNameContainingIgnoreCase(String brand, String name);
}
