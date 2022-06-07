package com.ebs.marketplace.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ebs.marketplace.payload.ProductDto;

import com.ebs.marketplace.model.Product;
import com.ebs.marketplace.service.ProductService;

@RestController
@RequestMapping("/api/user")
public class UserProductsController {
    @Autowired
    ProductService productService;

    @RequestMapping(value="/products", method=RequestMethod.POST)
    public Product createProduct(@RequestBody ProductDto productData) {
        return productService.createProduct(productData);
    }

    @RequestMapping(value="/products", method=RequestMethod.GET)
    public List<Product> readProducts() {
        return productService.getProducts();
    }

    @RequestMapping(value="/products/{product_id}", method=RequestMethod.PUT)
    public Product readProducts(@PathVariable(value = "product_id") Long id, @RequestBody Product productDetails) {
        return productService.updateProduct(id, productDetails);
    }

    @RequestMapping(value="/products/{product_id}", method=RequestMethod.DELETE)
    public void deleteProducts(@PathVariable(value = "product_id") Long id) {
        productService.deleteProduct(id);
    }

}
