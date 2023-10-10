package com.example.springbootbackendproject.Service;

import com.example.springbootbackendproject.entity.Order;
import com.example.springbootbackendproject.entity.Product;

import java.util.List;

public interface ProductService {
    public Product saveProduct(Product product);

    public List<Product> getAllProducts();

    public Product getProductById(Long productId);

    public void deleteProductById(Long productId);

    public Product updateProduct(Long productId, Product product);

    public List<Order> placeOrder(List<Order> orderList);

    public Long getCountOfTotalTransaction();
}
