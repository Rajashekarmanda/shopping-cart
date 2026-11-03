package com.codewithraj.shoppingcart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long id;

    private Long userId;

    private BigDecimal totalAmount;

    private List<CartItemDTO> cartItems;


}
