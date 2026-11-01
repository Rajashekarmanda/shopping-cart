package com.codewithraj.shoppingcart.request;

import com.codewithraj.shoppingcart.model.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotNull(message = "Price is required")
    @NotBlank(message = "Price must be greater then 0")
    private BigDecimal price;

    @PositiveOrZero(message = "Inventory cannot be negative")
    private int inventory;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Category is required")
    @Valid
    private Category category;
}
