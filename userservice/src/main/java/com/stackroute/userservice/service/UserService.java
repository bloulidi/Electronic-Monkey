package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface UserService {
    User saveUser(@Valid User user) throws UserAlreadyExistsException;
    User getUserById(int id) throws UserNotFoundException;
    User getUserByEmail(String email) throws UserNotFoundException;
    User deleteUser(int id) throws UserNotFoundException;
    User updateUser(User user) throws UserNotFoundException;
    List<User> getAllUsers();
    List<User> getUsersByName(String name);
    List<User> getUsersByAdmin(boolean admin);
}