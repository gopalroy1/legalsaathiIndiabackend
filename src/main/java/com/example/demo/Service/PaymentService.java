package com.example.demo.Service;

import com.example.demo.Dto.Request.PaymentRequest;
import com.example.demo.Dto.Request.VerifyPaymentRequest;
import com.example.demo.Enums.OrderStatus;
import com.example.demo.Enums.PaymentMode;
import com.example.demo.Enums.PaymentStatus;
import com.example.demo.Models.OrderModel;
import com.example.demo.Models.TransactionModel;
import com.example.demo.Repositary.OrderRepository;
import com.example.demo.Repositary.TransactionRepository;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class PaymentService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TransactionRepository transactionRepository;
    //
    @Value("${jwt.paymentSecret}")
    private String secret;

    //
    private static String toHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public boolean verifySignature(String orderId, String paymentId, String razorpaySignature) {
        try {
//            String secret = "2owwnNRnzH9T1qCixOvF8ivt";

            String payload = orderId + "|" + paymentId;

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte[] hashBytes = sha256_HMAC.doFinal(payload.getBytes());
            String expectedSignature = toHex(hashBytes);

            System.out.println("The payload is: " + payload);
            System.out.println("The signature from Razorpay is: " + razorpaySignature);
            System.out.println("The computed signature is: " + expectedSignature);

            return expectedSignature.equals(razorpaySignature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //to make a payment create order and also create a transaction with the status
    public  String saveTransactionAndVerifyPayment(VerifyPaymentRequest verifyPaymentRequest)throws  Exception{
        boolean isPaymentVerified=verifySignature(verifyPaymentRequest.razorPayOrderId,verifyPaymentRequest.paymentId,verifyPaymentRequest.razorpaySignature);
        System.out.println("Is payment verified"+isPaymentVerified);
        TransactionModel transaction = new TransactionModel();
        OrderModel order = orderRepository.getReferenceById(verifyPaymentRequest.orderId);
        transaction.setOrderId(verifyPaymentRequest.orderId);
        transaction.setAmount(verifyPaymentRequest.amount);

        transaction.setPaymentStatus(PaymentStatus.SUCCESS);
        order.setPaymentStatus(PaymentStatus.SUCCESS);
        if(!isPaymentVerified){
            transaction.setPaymentStatus(PaymentStatus.FAILED);
            order.setPaymentStatus(PaymentStatus.FAILED);
        }
        transaction.setPaymentMode(PaymentMode.UPI);
        transaction.setExternalTransactionId(verifyPaymentRequest.razorpaySignature);
        transaction.setPaymentReference(verifyPaymentRequest.refaranceNote);
        transaction.setMetadataJson(verifyPaymentRequest.metaData);

        transactionRepository.save(transaction);
        if(!isPaymentVerified){
            throw  new Exception("Could not verify payment try again !");
        }
//        OrderModel order = orderRepository.findOr

        return "Payment verified and saved";
    }
}
