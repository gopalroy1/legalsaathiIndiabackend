package com.example.demo.Service;

import com.example.demo.Enums.Role;
import com.example.demo.Models.UserModel;
import com.example.demo.Repositary.UserRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepositary userRepositary;
    public List<UserModel> getAllUsers(){
     return userRepositary.findAll();
    }
    public Optional<UserModel> getUserById(Long id)throws Exception{
        try {

        return  userRepositary.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void makeAdmin(Long id){
        UserModel userModel =userRepositary.findById(id).orElseThrow();
        userModel.setRole(Role.ADMIN);
        userRepositary.save(userModel);
    }
}
