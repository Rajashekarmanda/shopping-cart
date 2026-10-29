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

        // search category weather it's exist or not ?
        boolean foundCategory = categoryRepository.findByName(product.getCategory().getName()).isPresent();
        if (!foundCategory) {
            throw new NoSuchElementException("Category not exists!");
        }

        // create new category to store on product...
        Category category = new Category();
        Category reqCategory = categoryRepository.findByName(product.getCategory().getName()).get();
        category.setName(reqCategory.getName());
        category.setId(reqCategory.getId());

        // set category on product req...
        product.setCategory(category);

        // save product finally...
        return productRepository.save(product);
    }
}
