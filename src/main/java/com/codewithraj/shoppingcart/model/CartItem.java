package com.codewithraj.shoppingcart.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    @ManyToOne @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne @JoinColumn(name = "product_id")
    private Product product;

    public void recalculateTotalPrice(){
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
