package com.example.demo.Service;

import com.example.demo.Dto.Request.CreateProductRequest;
import com.example.demo.Dto.Request.UpdateProductRequest;
import com.example.demo.Models.ProductModel;
import com.example.demo.Repositary.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public String addProduct(CreateProductRequest createProductRequest){
            ProductModel productModel = new ProductModel();
            System.out.println("Inside product add service"+createProductRequest.getTitle()+createProductRequest.getPrice()+createProductRequest.getCategory());
            productModel.setTitle(createProductRequest.getTitle());
            productModel.setPrice(createProductRequest.getPrice());;
            productModel.setCategory(createProductRequest.getCategory());

        productRepository.save((productModel));
            return "Product added sucessfully";

    }
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
    public List<ProductModel> productModelList(){
        return productRepository.findAll();
    }
    public Optional<ProductModel> updateProduct(Long id, UpdateProductRequest request) {
        return productRepository.findById(id).map(product -> {
            product.setTitle(request.title);
            product.setPrice(request.price);
            product.setActive(request.isActive);
            return productRepository.save(product);
        });
    }
}
