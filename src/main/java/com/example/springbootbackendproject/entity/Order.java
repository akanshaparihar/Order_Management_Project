package com.example.springbootbackendproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Entity
@Document(collection = "orders")
public class Order{
    @Id
    private String orderId;

    private Long productId;
    private Long orderQuantity;
    private String orderType;
    private String message;

}
