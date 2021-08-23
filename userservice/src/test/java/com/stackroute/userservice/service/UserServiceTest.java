package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
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
        user = new User(1, "Anas", "anas@cgi.com", true, "password");
        user1 = new User(2, "Justin", "justin", false, "password");
        user2 = new User(3, "Justin", "justin@cgi.com", true, "password");
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
    public void givenUserWithDuplicateIdToSaveThenShouldNotReturnSavedUser() throws UserAlreadyExistsException {
        when(userRepository.existsById(user.getId())).thenReturn(true);
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(user));
        verify(userRepository, times(1)).existsById(anyInt());
    }

    @Test
    public void givenUserWithDuplicateEmailToSaveThenShouldNotReturnSavedUser() throws UserAlreadyExistsException {
        when(userRepository.existsById(user.getId())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(user));
        verify(userRepository, times(1)).existsById(anyInt());
        verify(userRepository, times(1)).existsByEmail(anyString());
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
        verify(userRepository, times(1)).findByName(anyString());
    }

   @Test
    public void givenGetAllUsersByAdminThenShouldReturnListOfAllAdminUsers() {
        userList.add(user);
        userList.add(user2);
        when(userRepository.findByAdmin(anyBoolean())).thenReturn(userList);
        assertEquals(userList, userService.getUsersByAdmin(user.isAdmin()));
        verify(userRepository, times(1)).findByAdmin(anyBoolean());
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
        verify(userRepository, times(1)).findById(anyInt());
    }

    @Test
    public void givenUserEmailThenShouldReturnRespectiveUser() throws UserNotFoundException {
        when(userRepository.findByEmail(any())).thenReturn(optional);
        assertEquals(user, userService.getUserByEmail(user.getEmail()));
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void givenUserEmailThenShouldNotReturnRespectiveUser() throws UserNotFoundException {
        when(userRepository.findByEmail(any())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(user.getEmail()));
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void givenUserIdToDeleteThenShouldReturnDeletedUser() throws UserNotFoundException {
        when(userRepository.findById(user.getId())).thenReturn(optional);
        User deletedUser = userService.deleteUser(user.getId());
        assertEquals(1, deletedUser.getId());

        verify(userRepository, times(1)).findById(anyInt());
        verify(userRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void givenUserIdToDeleteThenShouldNotReturnDeletedUser() throws UserNotFoundException {
        when(userRepository.findById(user.getId())).thenThrow(UserNotFoundException.class);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteUser(user.getId()));
        verify(userRepository, times(1)).findById(anyInt());
    }

    @Test
    public void givenUserToUpdateThenShouldReturnUpdatedUser() {
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.existsById(user.getId())).thenReturn(true);
        user.setPassword(user1.getPassword());
        User updatedUser = userService.updateUser(user);
        assertEquals(updatedUser.getPassword(), user1.getPassword());
        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(1)).existsById(anyInt());
    }

    @Test
    public void givenUserToUpdateThenShouldNotReturnUpdatedUser() throws UserNotFoundException {
        when(userRepository.existsById(user.getId())).thenReturn(false);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUser(user));
        verify(userRepository, times(1)).existsById(anyInt());
    }
}