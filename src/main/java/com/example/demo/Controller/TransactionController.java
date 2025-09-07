package com.example.demo.Controller;

import com.example.demo.Dto.Response.ApiResponse;
import com.example.demo.Models.TransactionModel;
import com.example.demo.Service.TransactionService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @GetMapping("/byOrder/{orderId}")
    public ApiResponse<List<TransactionModel>> getTransactionByOrders(@PathVariable Long orderId){
        try {
            List<TransactionModel> allTransactionByOrder= transactionService.getAllTransactionForAOrder(orderId);
            return new ApiResponse<>(HttpStatus.OK,"All Transaction by order fetched", allTransactionByOrder);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Unable to update order", null);
        }

    }
}
