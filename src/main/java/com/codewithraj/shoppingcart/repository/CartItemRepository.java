package com.codewithraj.shoppingcart.repository;

import com.codewithraj.shoppingcart.dto.CartItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemDTO, Long> {
    boolean existsByProductId(Long productId);
}
