package com.stackroute.userservice;

import com.stackroute.userservice.config.JWTTokenUtil;
import com.stackroute.userservice.controller.UserController;
import com.stackroute.userservice.repository.UserRepository;
import com.stackroute.userservice.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserApplicationTests {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JWTTokenUtil jwtTokenGenerator;

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertThat(userService).isNotNull();
        assertThat(jwtTokenGenerator).isNotNull();
        assertThat(userController).isNotNull();
        assertThat(userRepository).isNotNull();
    }
}
