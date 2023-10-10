package com.example.springbootbackendproject.repository;

import com.example.springbootbackendproject.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
