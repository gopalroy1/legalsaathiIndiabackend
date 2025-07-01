package com.example.demo.Dto.Response;


public class AuthResponse {
    public String message;
    public Long userId;
    public String token;

    public AuthResponse(String message, Long userId, String token) {
        this.message = message;
        this.userId = userId;
        this.token = token;
    }
}
