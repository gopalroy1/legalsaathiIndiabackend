package com.example.demo.Service;

import com.example.demo.Dto.Request.InitializeOrderRequest;
import com.example.demo.Dto.Request.UpdateOrderRequest;
import com.example.demo.Dto.Response.AllOrderResponse;
import com.example.demo.Dto.Response.InitializeOrderResponse;
import com.example.demo.Dto.Response.OrderByUserResponseDto;
import com.example.demo.Dto.Response.OrderDetailsResponseDto;
import com.example.demo.Enums.PaymentStatus;
import com.example.demo.Enums.ProductCategory;
import com.example.demo.Models.OrderModel;
import com.example.demo.Models.ProductModel;
import com.example.demo.Models.TransactionModel;
import com.example.demo.Models.UserModel;
import com.example.demo.Repositary.OrderRepository;
import com.example.demo.Repositary.ProductRepository;
import com.example.demo.Repositary.TransactionRepository;
import com.example.demo.Repositary.UserRepositary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype
        .Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepositary userRepositary;
    @Autowired
    TransactionRepository transactionRepository;

    public String createOrderInTable(InitializeOrderRequest initializeOrderRequest,BigDecimal amount){

        OrderModel order = new OrderModel();
        order.setUserId(initializeOrderRequest.userId);
        order.setProductId(initializeOrderRequest.productId);
        order.setAmount(amount);
        OrderModel savedOrder =orderRepository.save(order);
        System.out.println(savedOrder);
        return savedOrder.getId().toString();

    }

    public InitializeOrderResponse createOrder(InitializeOrderRequest initializeOrderRequest){
        ProductModel product = productRepository.findById(initializeOrderRequest.productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        BigDecimal amount=product.getPrice();
        BigDecimal razorPayAmount = amount.multiply(BigDecimal.valueOf(100));
        String razorPayOrderId =createOrderRazorpay(razorPayAmount);
        System.out.println("Razorpay order has been created"+razorPayOrderId);
        String tableOrderId = createOrderInTable(initializeOrderRequest,amount);
        InitializeOrderResponse initializeOrderResponse= new InitializeOrderResponse();
        initializeOrderResponse.razorpayOrderId=razorPayOrderId;
        initializeOrderResponse.orderId=tableOrderId;
        initializeOrderResponse.amount=amount;
        return  initializeOrderResponse;

    }


    public String createOrderRazorpay(BigDecimal amount) {
        // Razorpay order endpoint
        String postUrl = "https://api.razorpay.com/v1/orders";

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Basic Auth (Razorpay requires username:password base64-encoded in the header)
        String apiKey = "rzp_test_RYU5j5OJ4Rb4US";
        String secret = "2owwnNRnzH9T1qCixOvF8ivt";
        String auth = apiKey + ":" + secret;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);


        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode json = objectMapper.createObjectNode();
        json.put("amount", amount); // e.g., 10100 for ₹101
        json.put("currency", "INR");
        json.put("receipt", "receipt#1");

        // Add notes (optional)
        ObjectNode notes = objectMapper.createObjectNode();
        notes.put("key1", "value3");
        notes.put("key2", "value2");
        notes.put("key1", "value3");
        notes.put("key2", "value2");
        json.put("notes", notes);

        // Wrap JSON and headers in HttpEntity
        HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);

        // Make the API call
        RestTemplate restTemplate = new RestTemplate();
        //        {
//            "amount": 101,
//                "currency": "INR",
//                "receipt": "receipt#1",
//                "notes": {
//            "key1": "value3",
//                    "key2": "value2"
//        }
//        }
        String response = restTemplate.postForObject(postUrl, request, String.class);
        System.out.println("The razorpay response is "+response);
// Parse response
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        String orderId = jsonResponse.get("id").getAsString();
        return orderId;
    }
    //
    public TransactionModel getLatestTransactionByOrderId(List<TransactionModel> allTransactions, Long orderId) {
        System.out.println("running latest finding transaction");
        TransactionModel latestTransaction = null;
        for (TransactionModel transaction : allTransactions) {
            if (transaction.getOrderId().equals(orderId)) {
                if (latestTransaction == null || transaction.getUpdatedAt().isAfter(latestTransaction.getUpdatedAt())) {
                    latestTransaction = transaction;
                }
            }
        }
        return latestTransaction;
    }
    public List<AllOrderResponse> getAllOrders(){
        System.out.println("Get all order service is called");
        List<OrderModel> allOrders= orderRepository.findAll();
        System.out.println("Total order :"+allOrders.size());
//        List<TransactionModel> allTransactions= transactionRepository.findAll();
//        System.out.println("Total transaction :"+allTransactions.size());

        List<AllOrderResponse> allOrderResponsesList = new ArrayList<>();
        for(OrderModel order: allOrders){
            AllOrderResponse allOrderResponse = new AllOrderResponse();
            UserModel user = userRepositary.findById(order.getUserId()).orElseThrow();
            System.out.println("User fetched for order id "+order.getId());
            allOrderResponse.setUserId(user.getUserId());
            allOrderResponse.setUserName(user.getName());
            allOrderResponse.setOrderId(order.getId());
            allOrderResponse.setAmount(order.getAmount());

            ProductModel productModel = productRepository.findById(order.getProductId()).orElseThrow();
            System.out.println("Product fetched for order id "+productModel.getTitle());
            allOrderResponse.setProductName(productModel.getTitle());
            allOrderResponse.setProductId(productModel.getId());

//            TransactionModel transaction = getLatestTransactionByOrderId(allTransactions, order.getId());
//            if(transaction != null){
//                System.out.println("Latest transaction searched "+transaction.getAmount());
//                allOrderResponse.setPaymentStatus(order.getPaymentStatus());
//            } else {
//                allOrderResponse.setPaymentStatus(PaymentStatus.PENDING);
//            }

            allOrderResponse.setOrderStatus(order.getOrderStatus());
            allOrderResponse.setCreatedAt(order.getCreatedAt());
            allOrderResponse.setUpdatedAt(order.getUpdatedAt());
            allOrderResponse.setPaymentStatus(order.getPaymentStatus());
            allOrderResponse.setProductCategory(productModel.getCategory());

            System.out.println("adding inside all order response list "+allOrderResponse.getUserName()+allOrderResponse.getPaymentStatus());
            allOrderResponsesList.add(allOrderResponse);
            System.out.println("Added order in the response list "+allOrderResponsesList.size());
        }

        // 🔹 Sort latest first
        allOrderResponsesList.sort(
                Comparator.comparing(AllOrderResponse::getCreatedAt).reversed()
        );

        System.out.println("final all order (sorted)");
        return allOrderResponsesList;
    }
    public List<OrderByUserResponseDto> getAllOrderForAUser(Long userId) {
        List<OrderModel> orders = orderRepository.findByUserId(userId);

        return orders.stream()
                .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt())) // latest first
                .map(order -> {
                    ProductModel productModel = productRepository.findById(order.getProductId()).orElseThrow();
                    String productName = productModel.getTitle();
                    ProductCategory productCategory = productModel.getCategory();
                    return new OrderByUserResponseDto(order, productName,productCategory);
                })
                .toList();
    }
    //
//    public OrderDetailsResponseDto getOrderDetail(Long orderId) {
//        OrderModel order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));

//        TransactionModel transaction = transactionRepository.findByOrderId(orderId)
//                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Fetch product name (since OrderModel only has productId)
//        String productName = productRepository.findById(order.getProductId())
//                .map(ProductModel::getTitle)
//                .orElse("Unknown Product");

//        return new OrderDetailsResponseDto(
//                order.getId(),
//                productName,
//                order.getOrderStatus(),
//                transaction.getPaymentStatus(),
//                transaction.getAmount(),
//                transaction.getExternalTransactionId(),
//                transaction.getPaymentMode(),
//                transaction.getCreatedAt()
//        );
//    }
    public String updateOrder(UpdateOrderRequest updateOrderRequest){
        OrderModel order = orderRepository.getReferenceById(updateOrderRequest.orderId);
        order.setOrderStatus(updateOrderRequest.orderStatus);
        order.setPaymentStatus(updateOrderRequest.paymentStatus);
        orderRepository.save(order);
        return "Order updated sucessfully";
    }
}
