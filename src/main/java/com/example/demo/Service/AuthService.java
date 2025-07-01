package com.example.demo.Service;

import com.example.demo.Dto.Request.SignUpRequest;
import com.example.demo.Dto.Request.SingInRequest;
import com.example.demo.Dto.Response.AuthResponse;
import com.example.demo.Enums.Role;
import com.example.demo.Models.UserModel;
import com.example.demo.Repositary.UserRepositary;
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

    public AuthResponse registerUser(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email)) {
            throw new ResponseStatusException(BAD_REQUEST, "Email already registered");
        }

        UserModel user = new UserModel();
        user.setEmail(request.email);
        user.setPhone(request.phone);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setCity(request.city);
        user.setState(request.state);
        user.setPincode(request.pincode);
        user.setAddress(request.address);
        user.setRole(Role.USER);

        UserModel savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser.getEmail());

        return new AuthResponse("User registered successfully", savedUser.getUserId(), token);
    }

    public AuthResponse login(SingInRequest request) {
        UserModel user = userRepository.findByEmail(request.email)
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new ResponseStatusException(UNAUTHORIZED, "Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse("Login successful", user.getUserId(), token);
    }
}
