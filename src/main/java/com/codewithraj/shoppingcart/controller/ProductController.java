package com.codewithraj.shoppingcart.controller;

import com.codewithraj.shoppingcart.dto.ProductDTO;
import com.codewithraj.shoppingcart.model.Product;
import com.codewithraj.shoppingcart.exceptions.AlreadyExistsException;
import com.codewithraj.shoppingcart.response.ApiResponse;
import com.codewithraj.shoppingcart.exceptions.ResourceNotFoundException;
import com.codewithraj.shoppingcart.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts(@RequestParam (required = false) String name,
                                                        @RequestParam (required = false) String brand,
                                                        @RequestParam (required = false) String category) {
        List<Product> products = productService.getProducts(name, brand, category);
        List<ProductDTO> dtos = productService.convertToDTOList(products);
        return ResponseEntity.ok(new ApiResponse("Found " + products.size() + " products",dtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try{
            Product product = productService.getProductById(id);
            ProductDTO productDTO = productService.convertToDTO(product);
            return ResponseEntity.ok(new ApiResponse("Found product", productDTO));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addProduct(@Valid @RequestBody Product product) {
        try {
            Product savedProduct = productService.addProduct(product);
            ProductDTO savedProductDTO = productService.convertToDTO(savedProduct);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Added product", savedProductDTO));
        }catch (AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(e.getMessage(),null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        try {
            Product savedProduct = productService.updateProduct(id, product);
            ProductDTO savedProductDTO = productService.convertToDTO(savedProduct);
            return ResponseEntity.ok(new ApiResponse("Updated product", savedProductDTO));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try{
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse("Deleted product", null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> getProductCount(@RequestParam String brand, @RequestParam String name) {
        long count = productService.countProductsByBrandAndName(brand, name);
        return ResponseEntity.ok(new ApiResponse("Products found !", count));
    }

}
