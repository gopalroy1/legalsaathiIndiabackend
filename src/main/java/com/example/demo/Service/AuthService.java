package com.example.demo.Service;

import com.example.demo.Dto.Request.SignUpRequest;
import com.example.demo.Dto.Request.SingInRequest;
import com.example.demo.Dto.Response.AuthResponse;
import com.example.demo.Enums.Role;
import com.example.demo.Models.UserModel;
import com.example.demo.Repositary.UserRepositary;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class AuthService {

    @Autowired
    private UserRepositary userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AuthResponse registerUser(SignUpRequest request)throws Exception {
        if (userRepository.existsByEmail(request.email)) {
            throw new ResponseStatusException(BAD_REQUEST, "Email already registered");
        }

        UserModel user = new UserModel();
        user.setName(request.name);
        user.setEmail(request.email);
        user.setPhone(request.phone);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setCity(request.city);
        user.setState(request.state);
        user.setPincode(request.pincode);
        user.setAddress(request.address);
        user.setRole(Role.USER);

        UserModel savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser.getUserId(),user.getRole());

        return new AuthResponse("User registered successfully", savedUser.getUserId(), token,savedUser.getName(),savedUser.getRole());
    }

    public AuthResponse login(SingInRequest request) {
        UserModel user = userRepository.findByEmail(request.email)
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new ResponseStatusException(UNAUTHORIZED, "Invalid email or password");
        }

        String token = jwtService.generateToken(user.getUserId(),user.getRole());
        return new AuthResponse("Login successful", user.getUserId(), token,user.getName(),user.getRole());
    }
    public  boolean isAdmin(String cookie){
        jwtService.validateToken(cookie);
        Claims body = jwtService.getAllClaimsFromToken(cookie);
        System.out.println(body);
        return false;

    }
    public  boolean isLoggedIn(String cookie){
        jwtService.validateToken(cookie);
        Claims body = jwtService.getAllClaimsFromToken(cookie);
        System.out.println(body);
        return false;

    }
    public AuthResponse validateUser(String token) {
        // 1. Validate JWT
        jwtService.validateToken(token);

        // 2. Extract claims
        Claims claims = jwtService.getAllClaimsFromToken(token);
        Long userId = Long.valueOf(claims.getSubject()); // assuming subject = userId
        String roleStr = (String) claims.get("role");

        // 3. Lookup user
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "User not found"));

        // 4. Return AuthResponse (reuse existing structure)
        return new AuthResponse(
                "User validated successfully",
                user.getUserId(),
                token,
                user.getName(),
                user.getRole()
        );
    }


}
