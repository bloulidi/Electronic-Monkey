package com.stackroute.userservice.config;

import java.util.Map;

import com.stackroute.userservice.model.User;

public interface JWTTokenGenerator {
    Map<String, String> generateToken(User user);
}