package com.ebs.marketplace.controller;

import com.ebs.marketplace.model.Product;
import com.ebs.marketplace.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductsController {

    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getProducts(
            @RequestParam(defaultValue = "1", required = false) int pageNr,
            @RequestParam(defaultValue = "3", required = false) int pageSize) {
        return productService.getAllPosts(pageNr, pageSize);
    }

//    @SecurityRequirement(name = "Cookie")
    @PatchMapping("/products/{product_id}/{like}")
    public void likes(
            @PathVariable(value = "product_id") Long id,
            @PathVariable(value = "like") String like) {
        productService.likeDislikeManager(id, like);
    }
}
