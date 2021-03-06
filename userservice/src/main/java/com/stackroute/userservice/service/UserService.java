package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
public interface UserService {
    User saveUser(@Valid User user) throws UserAlreadyExistsException;

    User getUserById(@Min(value = 1, message = "The minimum id value should be 1") long id) throws UserNotFoundException;

    User getUserByEmail(@Email(message = "Please provide a valid email address") String email) throws UserNotFoundException;

    User getUserByEmailAndPassword(@Email(message = "Please provide a valid email address") String email,
                                   @NotBlank(message = "Password cannot be empty")
                                   @Size(min = 6, message = "Password must consist of at least 6 characters.") String password) throws UserNotFoundException;

    User deleteUser(@Min(value = 1, message = "The minimum id value should be 1") long id) throws UserNotFoundException;

    User updateUser(@Valid User user) throws UserNotFoundException, UserAlreadyExistsException;

    List<User> getAllUsers();

    List<User> getUsersByName(@NotBlank(message = "Name cannot be empty") String name);

    List<User> getUsersByAdmin(boolean admin);
}