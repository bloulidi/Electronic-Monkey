package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface UserService {
    User saveUser(@Valid User user) throws UserAlreadyExistsException;
    User getUserById(@NotNull(message = "ID cannot be null") @Min(1) int id) throws UserNotFoundException;
    User getUserByEmail(@Email(message = "Please provide a valid email address") String email) throws UserNotFoundException;
    User deleteUser(@NotNull(message = "ID cannot be null") @Min(1) int id) throws UserNotFoundException;
    User updateUser(@Valid User user) throws UserNotFoundException;
    List<User> getAllUsers();
    List<User> getUsersByName(@NotBlank(message = "Name cannot be empty") String name);
    List<User> getUsersByAdmin(boolean admin);
}