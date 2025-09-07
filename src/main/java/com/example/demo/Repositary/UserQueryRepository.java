package com.example.demo.Repositary;
import com.example.demo.Models.UserQueryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQueryRepository extends JpaRepository<UserQueryModel, Long> {
}
