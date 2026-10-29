package com.codewithraj.shoppingcart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "product name is required")
    private String name;

    @NotBlank(message = "brand is required")
    private String brand;

    @NotNull(message = "price is required")
    @Positive(message = "price should be greater then zero")
    private BigDecimal price;

    @PositiveOrZero(message = "inventory cannot be negative")
    private int inventory;

    private String description;

    @NotNull(message = "category is required")
    @ManyToOne
    @JoinColumn(name="category_id")
//    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
