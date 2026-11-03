package com.codewithraj.shoppingcart.exceptions;

import com.codewithraj.shoppingcart.dto.CartItemDTO;

import java.math.BigDecimal;
import java.util.List;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
