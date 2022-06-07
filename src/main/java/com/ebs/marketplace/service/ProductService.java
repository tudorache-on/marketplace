package com.ebs.marketplace.service;

import com.ebs.marketplace.payload.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ebs.marketplace.model.Product;
import com.ebs.marketplace.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    //Create
    public Product createProduct (ProductDto productData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        Product product = new Product();
        product.setTitle(productData.getTitle());
        product.setDescription(productData.getDescription());
        product.setPrice(productData.getPrice());
        product.setUserEmail(currentUserEmail);

        return productRepository.save(product);
    }

    //Read
    public List<Product> getProducts () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        return productRepository.findByUserEmail(currentUserEmail);
    }

    //Delete
    public void deleteProduct (Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        Product product = productRepository.findById(id).get();
        if (product.getUserEmail().equals(currentUserEmail))
            productRepository.deleteById(id);
    }

    // Update
    public Product updateProduct(Long prod_id, Product productDetails) {
        Product product = productRepository.findById(prod_id).get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        if (product.getUserEmail().equals(currentUserEmail)) {
            product.setTitle(productDetails.getTitle());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());

        }
        return productRepository.save(product);
    }

    // Get All Elements Of Page
    public List<Product> getAllPosts(int pageNr) {

        Pageable pageable = PageRequest.of(pageNr, 3);

        Page<Product> products = productRepository.findAll(pageable);

        return products.getContent();
    }

    public Product likeManager(Long id, Boolean like) {
        Product product = productRepository.findById(id).get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        List<String> likesList;

        if (product.getLikes() != null)
            likesList = new ArrayList<String>(Arrays.asList(product.getLikes().split(", ")));
        else
            likesList = new ArrayList<>();

        if (like != null && !currentUserEmail.equals(product.getUserEmail())) {

            if (product.getLikes().contains(currentUserEmail) && !like) {
                likesList.remove(currentUserEmail);
            }

            if (!product.getLikes().contains(currentUserEmail) && like) {
                likesList.add(currentUserEmail);
            }
        }
        if (likesList.size() > 0)
            if (likesList.get(0).equals(""))
                likesList.remove(0);

        product.setLikesCounter(likesList.size());
        product.setLikes(String.join(", ", likesList));

        return productRepository.save(product);
    }

}
