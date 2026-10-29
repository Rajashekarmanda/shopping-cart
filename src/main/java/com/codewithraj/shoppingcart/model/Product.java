package com.codewithraj.shoppingcart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
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
