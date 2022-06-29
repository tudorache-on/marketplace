package com.ebs.marketplace.controller;

import com.ebs.marketplace.model.Product;
import com.ebs.marketplace.payload.ProductDto;
import com.ebs.marketplace.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "Authentication")
@RequestMapping("/api/user")
public class UserProductsController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public Product createProduct(@RequestBody ProductDto productData) {
        return productService.createProduct(productData);
    }

    @GetMapping("/products")
    public List<Product> readProducts() {
        return productService.getProducts();
    }

    @PutMapping("/products/{product_id}")
    public Product readProducts(@PathVariable(value = "product_id") Long id, @RequestBody ProductDto productDetails) {
        return productService.updateProduct(id, productDetails);
    }

    @DeleteMapping("/products/{product_id}")
    public void deleteProducts(@PathVariable(value = "product_id") Long id) {
        productService.deleteProduct(id);
    }
}
