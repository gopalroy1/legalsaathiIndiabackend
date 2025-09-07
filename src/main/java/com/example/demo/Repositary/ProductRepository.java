package com.example.demo.Repositary;


import com.example.demo.Models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

//    boolean existsByEmail(String email);
}
