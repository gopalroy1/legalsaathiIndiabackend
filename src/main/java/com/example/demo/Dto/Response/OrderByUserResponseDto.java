package com.example.demo.Dto.Response;

import com.example.demo.Enums.ProductCategory;
import com.example.demo.Models.OrderModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderByUserResponseDto {
    private Long id;
    private BigDecimal amount;
    private String orderStatus;
    private String paymentStatus;
    private String productName;
    private ProductCategory productCategory;
    private LocalDateTime createdAt;

    // constructor
    public OrderByUserResponseDto(OrderModel order, String productName,ProductCategory productCategory) {
        this.id = order.getId();
        this.amount = order.getAmount();
        this.orderStatus = order.getOrderStatus().name();
        this.paymentStatus = order.getPaymentStatus().name();
        this.productName = productName;
        this.productCategory= productCategory;
        this.createdAt = order.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
}
