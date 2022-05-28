package com.example.testcontainersexample.integrationTest;

import com.example.testcontainersexample.entity.User;
import com.example.testcontainersexample.repository.UserRepository;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;
//started in,container for
@Testcontainers
@SpringBootTest
public class TestRepository {

    @Autowired
    private UserRepository userRepository;
    @Container
    private static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:12");

    @DynamicPropertySource
    static void sourceConfiguration(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }


    @Test
    void setUserRepository() {
        User user = new User()
                .setFirstName("Testcontainer")
                .setLastName("Testcontainerov");
        User saveUser = userRepository.save(user);
        Optional<User> userOptional = userRepository.findById(1);

        Assertions.assertTrue(userOptional.isPresent());
        Assertions.assertEquals(saveUser, userOptional.get());
    }
}
