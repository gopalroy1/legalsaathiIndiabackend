package com.example.demo.Models;

import com.example.demo.Enums.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private BigDecimal discountPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private boolean isDeleted = false;  // ✅ Soft delete flag

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

