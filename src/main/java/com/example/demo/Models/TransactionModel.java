package com.example.demo.Models;

import com.example.demo.Enums.PaymentMode;
import com.example.demo.Enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMode paymentMode = PaymentMode.MANUAL;

    @Column(length = 100)
    private String externalTransactionId; // Razorpay/Stripe/etc. txn ID

    @Column(length = 100)
    private String paymentReference; // Optional: any human-readable label

    @Column(columnDefinition = "TEXT")
    private String metadataJson; // Store full payload (gateway response or notes)

    @Column(nullable = false)
    private boolean isDeleted = false;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
