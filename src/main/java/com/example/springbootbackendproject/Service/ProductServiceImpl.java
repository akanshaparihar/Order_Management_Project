package com.example.springbootbackendproject.Service;

import com.example.springbootbackendproject.entity.Order;
import com.example.springbootbackendproject.entity.Product;
import com.example.springbootbackendproject.entity.Transaction;
import com.example.springbootbackendproject.exception.DuplicateProductNameException;
import com.example.springbootbackendproject.exception.ProductIdAlreadyExistsException;
import com.example.springbootbackendproject.exception.ProductNotFoundException;
import com.example.springbootbackendproject.repository.OrderRepository;
import com.example.springbootbackendproject.repository.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springbootbackendproject.repository.ProductRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService{
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public Product saveProduct(Product product) {
      /* if(productRepository.existsById(product.getId())) {
           throw new ProductIdAlreadyExistsException("Product with id : " +
                   product.getId() +
                   " already exists in the DB");
       }

        else return productRepository.save(product);*/
        String productName = product.getProductName();
        Product existingProduct = productRepository.findByProductName(productName);

        if (existingProduct != null && existingProduct.getId() != product.getId()) {
            throw new DuplicateProductNameException("Product with the same name already exists.");
        }

        return productRepository.save(product);
    }

        @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if(product.equals(null)){
            throw new RuntimeException("ProductId : " + productId + " does not exists");
        }
        return productRepository.findById(productId).get();

    }

    @Override
    public void deleteProductById(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product with ProductId : " + productId +
                    " does not exists so cannot be deleted");
        }
        productRepository.deleteById(productId);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Product productDB = productRepository.findById(productId).get();

        if(Objects.nonNull(product.getProductName()) && !"".equalsIgnoreCase(product.getProductName())){
            productDB.setProductName(product.getProductName());
        }

        if(Objects.nonNull(product.getProductCategory()) && !"".equalsIgnoreCase(product.getProductCategory())){
            productDB.setProductCategory(product.getProductCategory());
        }

        if(Objects.nonNull(product.getProductQuantity())){
            productDB.setProductQuantity(product.getProductQuantity());
        }

        if(Objects.nonNull(product.getProductPrice())){
            productDB.setProductPrice(product.getProductPrice());
        }

        return productRepository.save(productDB);
    }

    @Override
    public List<Order> placeOrder(List<Order> orderList) {
        boolean allOrdersPlaced = true; // Flag to check if all orders are placed

        for (Order order : orderList) {
            Product product = productRepository.findById(order.getProductId()).orElse(null);
            if (product == null) {
              //  order.setMessage("Product not found");
                allOrdersPlaced = false; // Set the flag to false as an order is not placed
                logger.error("Product not found for Order: {}", order);
                throw new ProductNotFoundException("Product not found");
               // continue; // Skip this order and proceed to the next one
            }

            long productQuantity = Objects.requireNonNullElse(product.getProductQuantity(), 0L);
            if (order.getOrderType().equals("purchase")) {
                product.setProductQuantity(productQuantity + order.getOrderQuantity());
                logger.info("Order placed: {}", order);
                productRepository.save(product);
              //  order.setMessage("Purchase Order is Placed Successfully");
            } else {
                if (order.getOrderQuantity() > productQuantity) {
                   // order.setMessage("Order is Not Placed: Not Enough Quantity Available");
                    allOrdersPlaced = false; // Set the flag to false as an order is not placed
                    throw new ProductNotFoundException("Order is Not Placed: Not Enough Quantity Available");
                } else {
                    product.setProductQuantity(productQuantity - order.getOrderQuantity());
                    logger.info("Order placed: {}", order);
                    productRepository.save(product);
                  //2  order.setMessage("Sale Order is Placed Successfully");
                }
            }

            // Giving unique orderId;
            UUID orderId = UUID.randomUUID();
            order.setOrderId(orderId.toString());

            orderRepository.save(order);
        }

        if (allOrdersPlaced) {
            Transaction transaction = new Transaction();
            UUID transactionId = UUID.randomUUID();
            transaction.setTransactionId(transactionId.toString());

            transaction.setOrderList(orderList);

            Transaction savedTransaction = transactionRepository.save(transaction);
            logger.info("Transaction saved with ID: {}", savedTransaction.getTransactionId());
           }
        else {
          //  System.out.println("Transaction not saved as some orders are not placed.");
            logger.warn("Transaction not saved as some orders are not placed.");
        }

        return orderList;
    }

    @Override
    public Long getCountOfTotalTransaction() {
        return transactionRepository.count();
    }
}
