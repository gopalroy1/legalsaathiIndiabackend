package com.example.demo.Controller;


import com.example.demo.Dto.Request.CreateProductRequest;
import com.example.demo.Dto.Request.UpdateProductRequest;
import com.example.demo.Models.ProductModel;
import com.example.demo.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping("/add")
    public ResponseEntity<String> addProducts(@Valid @RequestBody CreateProductRequest createProductRequest){
        try{
           String productAddResponseMessage= productService.addProduct((createProductRequest));
            return new ResponseEntity<>(productAddResponseMessage,HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);

        }
    }
    @GetMapping("/getall")
    public ResponseEntity<List<ProductModel>> getProducts(){
        try{
            List<ProductModel> productModelList=productService.productModelList();
            return  new ResponseEntity<>(productModelList,HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e);
            return  new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestBody UpdateProductRequest request
    ) {
        return productService.updateProduct(id, request)
                .map(updated -> ResponseEntity.ok(updated))
                .orElse(ResponseEntity.notFound().build());
    }
}
