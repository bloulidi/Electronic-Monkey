package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Validated
public interface UserService {
    User saveUser(@Valid User user) throws UserAlreadyExistsException;
    User getUserById(@NotNull(message = "ID cannot be null. Must be a positive integer excluding zero.") @Min(1) int id) throws UserNotFoundException;
    User getUserByEmail(@Email(message = "Please provide a valid email address") String email) throws UserNotFoundException;
    User deleteUser(@NotNull(message = "ID cannot be null. Must be a positive integer excluding zero.") @Min(1) int id) throws UserNotFoundException;
    User updateUser(@Valid User user) throws UserNotFoundException;
    List<User> getAllUsers();
    List<User> getUsersByName(@NotBlank(message = "Name cannot be empty") String name);
    List<User> getUsersByAdmin(@NotNull(message = "admin must be a boolean value: true | false") boolean admin);
}