package com.example.demo.Filters;

import com.example.demo.Models.UserModel;
import com.example.demo.Service.JwtService;
import com.example.demo.Service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
public class JwtFilter implements Filter {
    @Autowired
    JwtService jwtService;

    @Bean
    public List<String> publicPath() {
        return List.of("/api/auth/signin","/api/auth/signup","/api/auth/logout","/api/auth/singup","/api/auth/isLoggedIn","/queries/add","/products/getall");
    }
    public boolean isPublicpath(String url){
        System.out.println("Is public path method is called returning "+publicPath().stream().anyMatch(url::startsWith)+" incoming url "+url);
        return publicPath().stream().anyMatch(url::startsWith);
    }

    @Autowired
    UserService userService;
    public  boolean isLoggedIn(HttpServletRequest httpServletRequest){
        Cookie cookies [] = httpServletRequest.getCookies();
        String token = "";
        for(int i =0;i<cookies.length;i++){
            if( cookies[i].getName().equals("token")){
                token=cookies[i].getValue();
            }
        }
        return jwtService.validateToken(token);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
         HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //If public path then return
        System.out.println("Do filter method is called ");
        if(isPublicpath(httpServletRequest.getRequestURI())){
            System.out.println("We are allowing this public path ");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //Validate cookies and store the user details like role
         Cookie cookies []= httpServletRequest.getCookies();
        if(cookies==null || cookies.length==0){
            System.out.println("We have rec null or 0 cookies");

            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
        }
        System.out.println("The cookies rec length is "+ cookies.length);
        String token = "";
        for(int i =0;i<cookies.length;i++){
            if( cookies[i].getName().equals("token")){
                token=cookies[i].getValue();
            }
        }
        System.out.println("the token rec is"+token);
         if(jwtService.validateToken(token)){
             int userId = jwtService.getUserIdFromToken(token);
             try{
             UserModel userModel = userService.getUserById( (long)userId).orElseThrow();
                 Authentication auth = new UsernamePasswordAuthenticationToken(userModel,null,   List.of(new SimpleGrantedAuthority("ROLE_" + userModel.getRole().name())) );
                 SecurityContextHolder.getContext().setAuthentication(auth);
             } catch (Exception e) {
                 System.out.println(e);
                 httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                 return;
             }
             filterChain.doFilter(servletRequest, servletResponse);
         }else{
             httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
             return;
         }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
