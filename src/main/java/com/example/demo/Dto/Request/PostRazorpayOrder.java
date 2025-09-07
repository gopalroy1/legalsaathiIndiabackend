package com.example.demo.Dto.Request;

import java.math.BigDecimal;

public class PostRazorpayOrder {
//    {
//            "amount": 101,
//                "currency": "INR",
//                "receipt": "receipt#1",
//                "notes": {
//            "key1": "value3",
//                    "key2": "value2"
//        }
        public BigDecimal amount;
        public String receipt;
        public Notes notes;
        public class  Notes{
            String key1;
            String key2;
        }


}
