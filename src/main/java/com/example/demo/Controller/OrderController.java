package com.example.demo.Controller;

import com.example.demo.Dto.Request.InitializeOrderRequest;
import com.example.demo.Dto.Request.UpdateOrderRequest;
import com.example.demo.Dto.Response.*;
import com.example.demo.Models.OrderModel;
import com.example.demo.Models.UserModel;
import com.example.demo.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/add")
    public ApiResponse<InitializeOrderResponse> addOrders(@RequestBody InitializeOrderRequest initializeOrderRequest){
        try{
            System.out.println("Request came for creating a order "+initializeOrderRequest.userId+" Product id: "+initializeOrderRequest.productId);
            InitializeOrderResponse res = orderService.createOrder(initializeOrderRequest);
            return  new ApiResponse<>(HttpStatus.CREATED,"Order initialized successfully",res);
        } catch (Exception e) {
            System.out.println(e);
            return  new ApiResponse<>(HttpStatus.CREATED,"Failed to  initialized order",null);
        }
    }
    @GetMapping("/getall")
    public ApiResponse<List<AllOrderResponse>> getAllOrder(){
        try{

            return  new ApiResponse<>(HttpStatus.OK,"All orders fetched",orderService.getAllOrders());

        } catch (Exception e) {
            System.out.println(e);
            return  new ApiResponse<>(HttpStatus.BAD_REQUEST,"Unable to get all orders",null);
        }
    }
    @GetMapping("/getOrder")
    public ApiResponse<List<OrderByUserResponseDto>> getOrderByUser(@AuthenticationPrincipal UserModel user){
        try{
            List<OrderByUserResponseDto> orderByUserResponseDtos= orderService.getAllOrderForAUser(user.getUserId());
            return new ApiResponse<>(HttpStatus.OK,"Order Fetched successfully",orderByUserResponseDtos);
        } catch (Exception e) {
            System.out.println("Error while getting order for a user"+e.toString());
            return  new ApiResponse<>(HttpStatus.BAD_REQUEST,"Unable to get  orders for this user",null);

        }


    }
//    @GetMapping("/{orderId}")
//    public ApiResponse<OrderDetailsResponseDto> getOrderDetail(@PathVariable Long orderId) {
//        try {
//            OrderDetailsResponseDto orderDetails = orderService.getOrderDetail(orderId);
//            return new ApiResponse<>(HttpStatus.OK, "Order details fetched successfully", orderDetails);
//        } catch (Exception e) {
//            return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Unable to fetch order details", null);
//        }
//    }
    @PostMapping("/update")
    public  ApiResponse<String> updateOrder(@RequestBody UpdateOrderRequest updateOrderRequest){
        try{
            String res = orderService.updateOrder(updateOrderRequest);
            return new ApiResponse<>(HttpStatus.OK, res,null);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Unable to update order", null);

        }

    }
}
