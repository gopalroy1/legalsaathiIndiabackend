package com.example.demo.Controller;

import com.example.demo.Dto.Request.SignUpRequest;
import com.example.demo.Dto.Request.SingInRequest;
import com.example.demo.Dto.Response.AuthResponse;
import com.example.demo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public AuthResponse signUp(@RequestBody SignUpRequest request) {
        System.out.println("Un authorized printed for a test body");
        return authService.registerUser(request);
    }
    @PostMapping("/signin")
    public AuthResponse signIn(@RequestBody SingInRequest request) {
        return authService.login(request);
    }
}
