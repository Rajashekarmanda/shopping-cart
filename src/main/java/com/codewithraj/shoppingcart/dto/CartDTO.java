package com.codewithraj.shoppingcart.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDTO {

    private Long id;

    private Long userId;

    private BigDecimal totalAmount;

    private List<CartItemDTO> cartItems;


}
