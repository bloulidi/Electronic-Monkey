package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public User getUserById(long id) throws UserNotFoundException {
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
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws UserNotFoundException {
        User authUser = userRepository.findByEmailAndPassword(email, password);
        if (authUser == null) {
            throw new UserNotFoundException("Invalid email and/or password!");
        }
        return authUser;
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
    public User deleteUser(long id) throws UserNotFoundException {
        User user = getUserById(id);
        if(user != null){
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User updateUser(User user) throws UserNotFoundException, UserAlreadyExistsException {
        if(!userRepository.existsById(user.getId())){
            throw new UserNotFoundException();
        }
        User getUser = getUserById(user.getId());
        if(!getUser.getEmail().equals(user.getEmail())){
            if(userRepository.existsByEmail(user.getEmail())){
                throw new UserAlreadyExistsException();
            }
        }
        return (User) userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        if (user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }
}