package com.example.demo.Repositary;

import com.example.demo.Models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionModel,Long> {
    List<TransactionModel> findByOrderId(Long orderId);

}
