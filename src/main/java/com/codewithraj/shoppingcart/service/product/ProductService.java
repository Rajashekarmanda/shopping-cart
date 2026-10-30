package com.codewithraj.shoppingcart.service.product;

import com.codewithraj.shoppingcart.dto.CategoryDTO;
import com.codewithraj.shoppingcart.dto.ProductDTO;
import com.codewithraj.shoppingcart.model.Category;
import com.codewithraj.shoppingcart.model.Product;
import com.codewithraj.shoppingcart.repository.CategoryRepository;
import com.codewithraj.shoppingcart.repository.ProductRepository;
import com.codewithraj.shoppingcart.exceptions.AlreadyExistsException;
import com.codewithraj.shoppingcart.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        if (productRepository.existsByNameAndBrand(product.getName(), product.getBrand())) {
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
        Product foundProduct = getProductById(id);

        // checking category is existing or not
        Category categoryFound = categoryRepository.findByName(product.getCategory().getName())
                .orElseThrow(() -> new ResourceNotFoundException(product.getCategory().getName() + " Category not found!"));

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

    //  It will convert normal product obj into productDTO
    @Override
    public ProductDTO convertToDTO(Product product) {
        Category c = product.getCategory();
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getInventory(),
                product.getBrand(),
                product.getDescription(),
                product.getPrice(),
                new CategoryDTO(c.getId(), c.getName())
        );
    }

    //  It will convert normal list of product's obj into productDTO List
    @Override
    public List<ProductDTO> convertToDTOList(List<Product> products) {
        return products.stream().map(this::convertToDTO).toList();
    }
}
