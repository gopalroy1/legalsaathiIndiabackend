package com.example.demo.Service;

import com.example.demo.Enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {


    @Value("${jwt.secret}")
    private String secretKey;
    public String getSecretKey() {
        return secretKey;
    }
    public String generateToken(Long userId, Role role) {
        Map<String, Object> claims = new HashMap<>();
//        claims.put("role",role);
        claims.put("userId",userId);
        claims.put("role",role.name());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String getA(String str){
        System.out.println(getAllClaimsFromToken(str));
        return "";
    }
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            System.out.println("Token is validated");
            return true;
        } catch (Exception e) {
            System.out.println("Invalid token"+ e.toString());
        return false;
        }
    }
    public int getUserIdFromToken(String token){
       String sub = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
       System.out.println("The get user id from token ran and the subject is"+sub);
       return Integer.parseInt(sub);
    }


}

