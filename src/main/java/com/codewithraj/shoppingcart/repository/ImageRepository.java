package com.codewithraj.shoppingcart.repository;

import com.codewithraj.shoppingcart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
