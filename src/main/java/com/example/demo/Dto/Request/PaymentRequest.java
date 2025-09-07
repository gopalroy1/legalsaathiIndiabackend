package com.example.demo.Dto.Request;

import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.PaymentMode;
import com.example.demo.Enums.PaymentStatus;

import java.math.BigDecimal;

public class PaymentRequest {
    public long productId;
    public  long userId;
    public BigDecimal amount;
    public PaymentMode paymentMode;
    public PaymentStatus paymentStatus;
    public String externalTransactionId;
    public String paymentReference;
    public String metadataJson;
}
