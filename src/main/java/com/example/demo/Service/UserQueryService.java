package com.example.demo.Service;


import com.example.demo.Models.UserQueryModel;
import com.example.demo.Repositary.UserQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserQueryService {

    private final UserQueryRepository userQueryRepository;

    public UserQueryService(UserQueryRepository userQueryRepository) {
        this.userQueryRepository = userQueryRepository;
    }

    public UserQueryModel saveQuery(UserQueryModel query) {
        return userQueryRepository.save(query);
    }

    public List<UserQueryModel> getAllQueries() {
        return userQueryRepository.findAll();
    }
}

