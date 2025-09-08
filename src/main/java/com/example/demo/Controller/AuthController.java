package com.example.demo.Controller;

import com.example.demo.Dto.Request.SignUpRequest;
import com.example.demo.Dto.Request.SingInRequest;
import com.example.demo.Dto.Response.AuthResponse;
import com.example.demo.Service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.SystemEventListener;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody SignUpRequest request) {
        try {
            AuthResponse authResponse = authService.registerUser(request);
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.toString());
            AuthResponse authResponse = new AuthResponse(e.getMessage(), null, null, null, null);
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody SingInRequest request, HttpServletResponse response) {
        try {
            System.out.println("Inside the controller for sing in ");
            AuthResponse authResponse = authService.login(request);

            // Correctly configure CORS-safe cookie
            ResponseCookie tokenCookie = ResponseCookie.from("token", authResponse.token)
                    .httpOnly(true)
                    .secure(true) // ✅ true in production false in dev
                    .path("/")
                    .sameSite("Lax") // ✅ CRITICAL for cross-origin .. None in production and Lax in dev
                    .maxAge(24 * 60 * 60)
                    .build();

            response.addHeader("Set-Cookie", tokenCookie.toString());

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());
            AuthResponse authResponse = new AuthResponse(e.getMessage(), null, null, null, null);
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        System.out.println("Inside the controller for log out ");
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0); // ⛔ delete cookie
        response.addCookie(cookie);
        return ResponseEntity.ok("Logged out");
    }

    @GetMapping("/isAdmin")
    public ResponseEntity<String> isAdmin(HttpServletRequest httpServletRequest) {
        try {
            System.out.println("Callied is admin route");
            Cookie cookies[] = httpServletRequest.getCookies();
            String token = "";
            if (cookies == null) {
                throw new Error("We have null cookies");
            }
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
            if (token == "") {
                throw new Error("Please provide token");
            }
            System.out.println("The token recieved is " + token);
            authService.isAdmin(token);
            return new ResponseEntity<>("Verified", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>("Not verifed", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/isLoggedIn")
    public ResponseEntity<AuthResponse> isLoggedIn(HttpServletRequest request) {
        try {
            System.out.println("The is logged in auth controller is running");
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                return new ResponseEntity<>(new AuthResponse( "Token not validated",null,null,null,null ), HttpStatus.UNAUTHORIZED);
            }

            String token = null;
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }

            if (token == null || token.trim().isEmpty()) {
                return new ResponseEntity<>(new AuthResponse( "Token not validated",null,null,null,null ), HttpStatus.UNAUTHORIZED);
            }
            System.out.println("Token is present and auth service is triggering");
            // Validate and extract user info
            AuthResponse authResponse = authService.validateUser(token);
            System.out.println("The auth response for isLoggedIn "+authResponse);
            return new ResponseEntity<>(authResponse, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new AuthResponse( "Token not validated",null,null,null,null ), HttpStatus.UNAUTHORIZED);
        }
    }
}
