package com.codewithraj.shoppingcart.service.product;

import com.codewithraj.shoppingcart.dto.ProductDTO;
import com.codewithraj.shoppingcart.model.Product;

import java.util.List;

public interface IProductService {

    List<Product> getProducts(String name, String brand, String category);

    Product getProductById(Long id);

    Product addProduct(Product product);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    long countProductsByBrandAndName(String brand, String name);

    ProductDTO convertToDTO(Product product);

    List<ProductDTO> convertToDTOList(List<Product> products);
}
