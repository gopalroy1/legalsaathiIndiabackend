package com.example.demo.Controller;

import com.example.demo.Dto.Request.MakeAdminPayload;
import com.example.demo.Models.UserModel;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/getall")
    public ResponseEntity<List<UserModel>> getAllUserController(){
        System.out.println("Called get users");
        List<UserModel> users =userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @PostMapping("/makeadmin")
    public ResponseEntity<String> makeAdminController(@RequestBody MakeAdminPayload makeAdminPayload){
        try{

        System.out.println("Make admin controller called");
        userService.makeAdmin(makeAdminPayload.id);
        return new ResponseEntity<>("Sucessfully made admin",HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return  new ResponseEntity<>("Failed to make admin",HttpStatus.BAD_REQUEST);
        }
    }

}
