package com.example.demo.Controller;

import com.example.demo.Dto.Request.PaymentRequest;
import com.example.demo.Dto.Request.VerifyPaymentRequest;
import com.example.demo.Dto.Response.ApiResponse;
import com.example.demo.Dto.Response.InitializeOrderResponse;
import com.example.demo.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

//    @PostMapping("/add")
//    public ApiResponse<String> addPaymentController(@RequestBody PaymentRequest paymentRequest){
//        try {
//            System.out.println("Request came for payment of "+paymentRequest.amount+"User:"+paymentRequest.userId +"Product:"+paymentRequest.productId+"Status: "+paymentRequest.paymentStatus);
//            String res = paymentService.makePayment(paymentRequest);
//            return new ApiResponse<>(HttpStatus.CREATED,"Order created successfully",res);
//        } catch (Exception e) {
//            System.out.println(e);
//            return new ApiResponse<>(HttpStatus.CREATED,"Could not create order ",null);
//        }
//    }

    @PostMapping("/verify")
    public  ApiResponse<String> saveTransactionAndVerifyPayment(@RequestBody VerifyPaymentRequest verifyPaymentRequest){
        try{
            System.out.println("inside verify controller , "+verifyPaymentRequest.razorPayOrderId+" u"+verifyPaymentRequest.paymentId);
            String res = paymentService.saveTransactionAndVerifyPayment(verifyPaymentRequest);
            return  new ApiResponse<>(HttpStatus.CREATED,res,res);
        } catch (Exception e) {
            System.out.println(e);
            return  new ApiResponse<>(HttpStatus.BAD_REQUEST,"Unable to verify transaction",null);
        }
    }
}
