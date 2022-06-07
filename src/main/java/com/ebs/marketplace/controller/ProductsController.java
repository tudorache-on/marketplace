package com.ebs.marketplace.controller;

import com.ebs.marketplace.model.Product;
import com.ebs.marketplace.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductsController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/products", method= RequestMethod.GET)
    public List<Product> getProducts(
            @RequestParam(defaultValue = "0", required = false) int pageNr
    ){
        return productService.getAllPosts(pageNr);
    }

    @RequestMapping(value = "/product/{product_id}", method= RequestMethod.GET)
    public Product likes(
            @PathVariable (value = "product_id") Long id,
            @RequestParam(required = false) Boolean like
    ){
        return productService.likeManager(id, like);
    }
}
