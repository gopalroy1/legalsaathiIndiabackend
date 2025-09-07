package com.example.demo.Dto.Response;


import com.example.demo.Enums.Role;

public class AuthResponse {
    public String message;
    public Long userId;
    public String token;
    public String userName;
    public Role role;
public  AuthResponse(){}

    public AuthResponse(String message, Long userId, String token, String userName, Role role) {
        this.message = message;
        this.userId = userId;
        this.token = token;
        this.userName = userName;
        this.role = role;
    }
}
