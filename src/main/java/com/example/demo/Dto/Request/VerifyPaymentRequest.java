package com.example.demo.Dto.Request;

import java.math.BigDecimal;

public class VerifyPaymentRequest {
    public  long orderId;
    public String razorPayOrderId;
    public String paymentId;
    public String razorpaySignature;
    public String userId;
    public BigDecimal amount;
    public String refaranceNote;
    public String metaData;
}
