package com.example.demo.Dto.Request;

import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.PaymentStatus;

public class UpdateOrderRequest {
    public Long orderId;
    public PaymentStatus paymentStatus;
    public OrderStatus orderStatus;
}
