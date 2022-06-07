package com.ebs.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebs.marketplace.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUserEmail(String userEmail);
}
