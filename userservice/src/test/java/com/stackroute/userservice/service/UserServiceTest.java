package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.Login;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.model.UserDetails;
import com.stackroute.userservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;
    private User user, user1, user2;
    private List<User> userList;
    private Optional optional;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userList = new ArrayList<User>();
        user = new User(1, "Anas", "anas@cgi.com", true, new Login("username", "password"), new UserDetails());
        user1 = new User(2, "Justin", "justin", false, new Login("username", "password"), new UserDetails("m", Date.valueOf("1996-12-15"), "1350 René-Lévesque", "5147384141"));
        user2 = new User(3, "Justin", "justin@cgi.com", true, new Login("username", "password"), new UserDetails());
        optional = Optional.of(user);
    }

    @AfterEach
    public void tearDown() {
        user = null;
    }

    @Test
    public void givenUserToSaveThenShouldReturnSavedUser() throws UserAlreadyExistsException {
        when(userRepository.save(any())).thenReturn(user);
        assertEquals(user, userService.saveUser(user));
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void givenUserToSaveThenShouldNotReturnSavedUser() throws UserAlreadyExistsException {
        when(userRepository.save(any())).thenThrow(new UserAlreadyExistsException());
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(user));
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void givenGetAllUsersThenShouldReturnListOfAllUsers() {
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        assertEquals(userList, userService.getAllUsers());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void givenGetAllUsersByNameThenShouldReturnListOfAllRespectiveUsers() {
        userList.add(user1);
        userList.add(user2);
        when(userRepository.findByName(any())).thenReturn(userList);
        assertEquals(userList, userService.getUsersByName(user1.getName()));
        verify(userRepository, times(1)).findByName(user1.getName());
    }

   @Test
    public void givenGetAllUsersByAdminThenShouldReturnListOfAllAdminUsers() {
        userList.add(user);
        userList.add(user2);
        when(userRepository.findByAdmin(anyBoolean())).thenReturn(userList);
        assertEquals(userList, userService.getUsersByAdmin(user.isAdmin()));
        verify(userRepository, times(1)).findByAdmin(user.isAdmin());
    }

    @Test
    public void givenUserIdThenShouldReturnRespectiveUser() throws UserNotFoundException {
        when(userRepository.findById(anyInt())).thenReturn(optional);
        assertEquals(user, userService.getUserById(user.getId()));
        verify(userRepository, times(1)).findById(anyInt());
    }

    @Test
    void givenUserIdThenShouldNotReturnRespectiveUser() throws UserNotFoundException {
        when(userRepository.findById(any())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserById(user.getId()));
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    public void givenUserEmailThenShouldReturnRespectiveUser() throws UserNotFoundException {
        when(userRepository.findByEmail(any())).thenReturn(optional);
        assertEquals(user, userService.getUserByEmail(user.getEmail()));
        verify(userRepository, times(1)).findByEmail(any());
    }

    @Test
    void givenUserEmailThenShouldNotReturnRespectiveUser() throws UserNotFoundException {
        when(userRepository.findByEmail(any())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(user.getEmail()));
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void givenUserIdToDeleteThenShouldReturnDeletedUser() throws UserNotFoundException {
        when(userRepository.findById(user.getId())).thenReturn(optional);
        User deletedUser = userService.deleteUser(user.getId());
        assertEquals(1, deletedUser.getId());

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void givenUserIdToDeleteThenShouldNotReturnDeletedUser() throws UserNotFoundException {
        when(userRepository.findById(user.getId())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1));
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    public void givenUserToUpdateThenShouldReturnUpdatedUser() {
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.existsById(user.getId())).thenReturn(true);
        assertEquals(user, userService.saveUser(user));
        user.setUserDetails(user1.getUserDetails());
        User updatedUser = userService.updateUser(user);
        assertEquals(updatedUser.getUserDetails(), user1.getUserDetails());
        verify(userRepository, times(2)).save(any());
        verify(userRepository, times(1)).existsById(user.getId());
    }

    @Test
    public void givenUserToUpdateThenShouldNotReturnUpdatedUser() throws UserNotFoundException {
        when(userRepository.existsById(user.getId())).thenReturn(false);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUser(user));
        verify(userRepository, times(1)).existsById(user.getId());
    }
}