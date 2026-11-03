package com.codewithraj.shoppingcart.repository;

import com.codewithraj.shoppingcart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    boolean existsByProductId(Long productId);
}
