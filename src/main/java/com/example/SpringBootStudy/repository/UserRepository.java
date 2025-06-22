package com.example.SpringBootStudy.repository;

import com.example.SpringBootStudy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
