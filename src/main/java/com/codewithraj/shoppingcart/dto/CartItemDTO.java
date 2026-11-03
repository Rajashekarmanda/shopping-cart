package com.codewithraj.shoppingcart.dto;

import java.math.BigDecimal;

public class CartItemDTO {

    private Long id;

    private Long productId;

    private String productName;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;
}
