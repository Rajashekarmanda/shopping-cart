package com.codewithraj.shoppingcart.service.product;

import com.codewithraj.shoppingcart.model.Category;
import com.codewithraj.shoppingcart.model.Product;
import com.codewithraj.shoppingcart.repository.CategoryRepository;
import com.codewithraj.shoppingcart.repository.ProductRepository;
import com.codewithraj.shoppingcart.response.AlreadyExistsException;
import com.codewithraj.shoppingcart.response.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public Product addProduct(Product product) {
        if (productRepository.findByName(product.getName()).isPresent()) {
            throw new AlreadyExistsException("Product already exists!");
        }
        Category category = new Category();
        if (categoryRepository.findByName(product.getName()).isEmpty()) {
            throw new NoSuchElementException("Category not exists!");
        }
        Category reqCategory = categoryRepository.findByName(product.getCategory().getName()).get();
        category.setName(reqCategory.getName());
        category.setId(reqCategory.getId());

        product.setCategory(category);
        return productRepository.save(product);
    }
}
