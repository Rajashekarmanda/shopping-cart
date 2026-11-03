package com.codewithraj.shoppingcart.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCartItemRequest {

    @NotNull
    private Long productId;

    @Min(value = 1, message = "quantity cannot be less then 1")
    private int quantity;
}
