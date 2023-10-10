package com.example.springbootbackendproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Entity
@Data
@Document(collection = "transactions")
public class Transaction {
    @Id
    public String transactionId;
    private List<Order> orderList;
}
