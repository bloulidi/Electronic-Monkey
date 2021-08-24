package com.stackroute.userservice.config;

import com.stackroute.userservice.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JWTTokenGeneratorImplTest {
    private User user;

    @Autowired
    private JWTTokenGeneratorImpl jwtTokenGenerator;
    private Map<String, String> tokenMap;

    @BeforeEach
    public void setup() {
        user = new User(1, "Anas", "anas@cgi.com", false, "password");
        tokenMap = new HashMap<>();
    }

    @Test
    void givenAUserThenCallToGenerateTokenShouldReturnNotNull() {
        tokenMap = jwtTokenGenerator.generateToken(user);
        assertNotNull(tokenMap);
    }

    @Test
    void givenAUserThenShouldReturnExpectedKeysInMap() {
        tokenMap = jwtTokenGenerator.generateToken(user);
        assertNotNull(tokenMap.containsKey("token"));
        assertNotNull(tokenMap.containsKey("message"));
    }

    @Test
    void givenAUserThenShouldReturnExpectedTokenInMap() {
        tokenMap = jwtTokenGenerator.generateToken(user);
        assertThat(tokenMap.get("token").length()).isGreaterThan(20);
    }

    @Test
    void givenAUserThenShouldReturnExpectedClaimInMap() {
        tokenMap = jwtTokenGenerator.generateToken(user);
        assertThat(tokenMap.get("message")).isEqualTo("Login Successful");
    }
}