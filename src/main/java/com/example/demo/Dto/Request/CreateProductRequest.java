package com.example.demo.Dto.Request;

import com.example.demo.Enums.ProductCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


public class CreateProductRequest {

    @NotBlank(message = "Product title is requried")
    private String title;
    @NotNull(message = "Product price is requried")
    @DecimalMin(value = "0.0",inclusive = false,message = "product price must be greater than 0")
    private BigDecimal price;
    @NotNull(message = "Product category is requried")
    private ProductCategory category;

    public @NotBlank(message = "Product title is requried") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Product title is requried") String title) {
        this.title = title;
    }

    public @NotNull(message = "Product price is requried") @DecimalMin(value = "0.0", inclusive = false, message = "product price must be greater than 0") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Product price is requried") @DecimalMin(value = "0.0", inclusive = false, message = "product price must be greater than 0") BigDecimal price) {
        this.price = price;
    }

    public @NotNull(message = "Product category is requried") ProductCategory getCategory() {
        return category;
    }

    public void setCategory(@NotNull(message = "Product category is requried") ProductCategory category) {
        this.category = category;
    }
}
