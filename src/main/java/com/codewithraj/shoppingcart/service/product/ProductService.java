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
    public List<Product> getProducts(String name, String brand, String category) {
        return productRepository.findByFilters(name, brand, category);
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
        Category foundCategory = categoryRepository.findByName(product.getCategory().getName())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

        // set category on product req...
        product.setCategory(foundCategory);

        // save product finally...
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));


        // checking category is existing or not
        Category categoryFound = categoryRepository.findByName(product.getCategory().getName())
                .orElseThrow(() -> new ResourceNotFoundException(product.getCategory().getName() + " Category not found!"));

        // check product is existing or not
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found! with ID "+ id));

        // update details with new data
        foundProduct.setName(product.getName());
        foundProduct.setInventory(product.getInventory());
        foundProduct.setBrand(product.getBrand());
        foundProduct.setDescription(product.getDescription());
        foundProduct.setPrice(product.getPrice());
        foundProduct.setCategory(categoryFound);

        //save
        return productRepository.save(foundProduct);

    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Product not found!");
        }
        productRepository.deleteById(id);
    }

    @Override
    public long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndNameContainingIgnoreCase(brand, name);
    }
}
