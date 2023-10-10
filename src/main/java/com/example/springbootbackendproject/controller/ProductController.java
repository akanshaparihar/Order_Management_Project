package com.example.springbootbackendproject.controller;

import com.example.springbootbackendproject.Service.ProductService;
import com.example.springbootbackendproject.entity.Order;
import com.example.springbootbackendproject.entity.Product;
import com.example.springbootbackendproject.exception.DuplicateProductNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/")
    public String landingPage(){
        return ("Welcome To Springboot mini Project");
    }

    @PostMapping("/products")
   public Product saveProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

@GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long productId){
        return productService.getProductById(productId);
    }

    @DeleteMapping("/products/{id}")
    public String deleteProductById(@PathVariable("id") Long productId){
        productService.deleteProductById(productId);
        return ("Product deleted successfully");
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") Long productId, @RequestBody Product product){
        return productService.updateProduct(productId, product);
    }

    @PostMapping("/placeOrder")
    public List<Order> placeOrder(@Valid @RequestBody List<Order> orderList){
        return productService.placeOrder(orderList);
    }

    @GetMapping("/countTransactions")
    public Long getCountOfTotalTransaction(){
        return productService.getCountOfTotalTransaction();
    }

}
