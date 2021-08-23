package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        if(userRepository.existsById(user.getId()) || userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException();
        }
        return (User) userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        User user = null;
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            user = userOptional.get();
        } else {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        User user = null;
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            user = userOptional.get();
        } else {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public List<User> getUsersByName(String name) {
        return (List<User>) userRepository.findByName(name);
    }

    @Override
    public List<User> getUsersByAdmin(boolean admin) {
        return (List<User>) userRepository.findByAdmin(admin);
    }

    @Override
    public User deleteUser(int id) throws UserNotFoundException {
        User user = getUserById(id);
        if(user != null){
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException {
        if(!userRepository.existsById(user.getId())){
            throw new UserNotFoundException();
        }
        return (User) userRepository.save(user);
    }
}