package com.example.demo.Service;

import com.example.demo.Models.TransactionModel;
import com.example.demo.Repositary.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    public List<TransactionModel> getAllTransactionForAOrder(Long orderId){
       List<TransactionModel> transactionModelList= transactionRepository.findByOrderId(orderId);
        transactionModelList.sort((t1, t2) -> t2.getId().compareTo(t1.getId())); // latest first
        return transactionModelList;
    }
}
