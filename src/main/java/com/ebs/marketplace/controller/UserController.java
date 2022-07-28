package com.ebs.marketplace.controller;

import com.ebs.marketplace.model.Notification;
import com.ebs.marketplace.model.Product;
import com.ebs.marketplace.model.ProductDto;
import com.ebs.marketplace.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "Authentication")
@RequestMapping("/api/users")
public class UserController {
    private final ProductService productService;

    @Autowired
    public UserController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public Product createProduct(@RequestBody ProductDto productData) {
        return productService.createProduct(productData);
    }

    @GetMapping("/products")
    public List<Product> readProducts() {
        return productService.getProducts();
    }

    @PutMapping("/products/{product_id}")
    public Product updateProduct(@PathVariable(value = "product_id") Long id, @RequestBody ProductDto productDetails) {
        return productService.updateProduct(id, productDetails);
    }

    @DeleteMapping("/products/{product_id}")
    public void deleteProducts(@PathVariable(value = "product_id") Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/notifications")
    public List<Notification> readNotifications() {
        return productService.getNotifications();
    }

    @PatchMapping("/notifications/{notification_id}")
    public void updateNotification(@PathVariable(value = "notification_id") long id) {
        productService.updateNotification(id);
    }

    @DeleteMapping("/notifications/{notification_id}")
    public void deleteNotification (@PathVariable(value = "notification_id") long id) {
        productService.deleteNotification(id);
    }
}
