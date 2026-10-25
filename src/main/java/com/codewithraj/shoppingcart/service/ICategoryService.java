package com.codewithraj.shoppingcart.service;

import com.codewithraj.shoppingcart.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
}
