package com.example.springbootbackendproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Data
@Document(collection = "products")
public class Product {
    @Id
    private Long id;
    private String productName;
    private Long productQuantity;
    private double productPrice;
    private String productCategory;
}
