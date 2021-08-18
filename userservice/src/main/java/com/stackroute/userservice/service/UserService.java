package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user) throws UserAlreadyExistsException;
    User getUserById(int id) throws UserNotFoundException;
    User getUserByEmail(String email) throws UserNotFoundException;
    User deleteUserById(int id) throws UserNotFoundException;
    User updateUser(User user) throws UserNotFoundException;
    List<User> getAllUsers();
    List<User> getUsersByName(String name);
}
