package com.example.testcontainersexample.repository;

import com.example.testcontainersexample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
