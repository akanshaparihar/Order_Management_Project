package com.example.springbootbackendproject.repository;

import com.example.springbootbackendproject.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepository extends MongoRepository<Product, Long> {
    Product findByProductName(String productName);
}
