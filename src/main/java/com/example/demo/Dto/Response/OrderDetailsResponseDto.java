package com.example.demo.Dto.Response;


import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.PaymentMode;
import com.example.demo.Enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class OrderDetailsResponseDto {
    private Long orderId;
    private String productName;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private BigDecimal transactionAmount;
    private String transactionId;
    private PaymentMode paymentMode;
    private LocalDateTime transactionDate;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OrderDetailsResponseDto() {
    }

    public OrderDetailsResponseDto(Long orderId, String productName, OrderStatus orderStatus,PaymentStatus paymentStatus, BigDecimal transactionAmount, String transactionId, PaymentMode paymentMode, LocalDateTime transactionDate) {
        this.orderId = orderId;
        this.productName = productName;
        this.orderStatus = orderStatus;
        this.paymentStatus= paymentStatus;
        this.transactionAmount = transactionAmount;
        this.transactionId = transactionId;
        this.paymentMode = paymentMode;
        this.transactionDate = transactionDate;
    }
}

