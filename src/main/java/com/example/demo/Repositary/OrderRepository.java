package com.example.demo.Repositary;

import com.example.demo.Models.OrderModel;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel,Long > {
    List<OrderModel> findByUserId(Long userId);

}
