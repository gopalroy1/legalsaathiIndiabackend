package com.example.demo.Controller;


import com.example.demo.Models.UserQueryModel;
import com.example.demo.Service.UserQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queries")
public class UserQueryController {

    private final UserQueryService userQueryService;

    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    // Endpoint for visitors to submit a query
    @PostMapping("/add")
    public ResponseEntity<UserQueryModel> submitQuery(@RequestBody UserQueryModel query) {
        return ResponseEntity.ok(userQueryService.saveQuery(query));
    }

    // Endpoint for admin to fetch all queries
    @GetMapping("/getAll")
    public ResponseEntity<List<UserQueryModel>> getAllQueries() {
        return ResponseEntity.ok(userQueryService.getAllQueries());
    }
}

