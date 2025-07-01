package com.example.demo.Repositary;


import com.example.demo.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositary extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);
    boolean existsByEmail(String email);
}
