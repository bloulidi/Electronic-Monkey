package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    private User user1, user2, user3;
    private List<User> userList;
    private List<String> usersEmail, savedUsersEmail;

    @BeforeEach
    void setUp() {
        user1 = new User(1, "Anas", "anas@cgi.com", true, "password1");
        user2 = new User(2, "Justin", "justin@hotmail.com", false, "password2");
        user3 = new User(3, "Justin", "justin@cgi.com", true, "password3");
        userList = new ArrayList<User>();
        savedUsersEmail = new ArrayList<>();
        usersEmail = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        user1 = user2 = user3 = null;
        userList = null;
        usersEmail = savedUsersEmail = null;
    }

    @Test
    public void givenUserToSaveThenShouldReturnSavedUser() throws UserAlreadyExistsException {
        User savedUser = userService.saveUser(user1);
        assertNotNull(savedUser);
        assertEquals(user1.getEmail(), savedUser.getEmail());
    }

    @Test
    public void givenUserToSaveThenShouldNotReturnSavedUser() throws UserAlreadyExistsException {
        userService.saveUser(user1);
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(user1));
    }

    @Test
    public void givenUserWithDuplicateEmailToSaveThenShouldNotReturnSavedUser() throws UserAlreadyExistsException {
        User savedUser = userService.saveUser(user1);
        assertNotNull(savedUser);
        user2.setEmail(user1.getEmail());
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(user2));
    }

    @Test
    public void givenGetAllUsersThenShouldReturnListOfAllUsers() throws UserAlreadyExistsException {
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        List<User> users = userService.getAllUsers();
        for (User user : userList) { usersEmail.add(user.getEmail()); }
        for (User user : users) { savedUsersEmail.add(user.getEmail()); }
        assertNotNull(users);
        assertEquals(usersEmail, savedUsersEmail);
    }

    @Test
    public void givenGetAllUsersByNameThenShouldReturnListOfAllRespectiveUsers() throws UserAlreadyExistsException {
        userList.add(user2);
        userList.add(user3);
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        List<User> users = userService.getUsersByName(user2.getName());
        for (User user : userList) { usersEmail.add(user.getEmail()); }
        for (User user : users) { savedUsersEmail.add(user.getEmail()); }
        assertNotNull(users);
        assertEquals(usersEmail, savedUsersEmail);
    }

    @Test
    public void givenGetAllUsersByAdminThenShouldReturnListOfAllAdminUsers() throws UserAlreadyExistsException {
        userList.add(user1);
        userList.add(user3);
        userService.saveUser(user1);
        //userService.saveUser(user2);
        userService.saveUser(user3);
        List<User> users = userService.getUsersByAdmin(user1.isAdmin());
        for (User user : userList) { usersEmail.add(user.getEmail()); }
        for (User user : users) { savedUsersEmail.add(user.getEmail()); }
        assertNotNull(users);
        assertEquals(usersEmail, savedUsersEmail);
    }

    @Test
    public void givenUserIdThenShouldReturnRespectiveUser() throws UserAlreadyExistsException, UserNotFoundException {
        User savedUser = userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        User getUser = userService.getUserById(savedUser.getId());
        assertNotNull(getUser);
        assertEquals(user1.getEmail(), getUser.getEmail());
    }

    @Test
    void givenUserIdThenShouldNotReturnRespectiveUser() throws UserNotFoundException {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserById(user1.getId()));
    }

    @Test
    public void givenUserEmailThenShouldReturnRespectiveUser() throws UserAlreadyExistsException, UserNotFoundException {
        User savedUser = userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        User getUser = userService.getUserByEmail(savedUser.getEmail());
        assertNotNull(getUser);
        assertEquals(user1.getEmail(), getUser.getEmail());
    }

    @Test
    void givenUserEmailThenShouldNotReturnRespectiveUser() throws UserNotFoundException {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(user1.getEmail()));
    }

    @Test
    void givenUserEmailAndPasswordThenShouldReturnRespectiveUser() throws UserAlreadyExistsException, UserNotFoundException {
        User savedUser = userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        User getUser = userService.getUserByEmailAndPassword(savedUser.getEmail(), savedUser.getPassword());
        assertNotNull(getUser);
        assertEquals(user1.getEmail(), getUser.getEmail());
    }

    @Test
    void givenValidUserEmailAndInvalidPasswordThenShouldNotReturnRespectiveUser() throws UserAlreadyExistsException, UserNotFoundException {
        User savedUser = userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        user1.setPassword("sdfsdf");
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserByEmailAndPassword(savedUser.getEmail(), user1.getPassword()));
    }

    @Test
    void givenInvalidUserEmailAndValidPasswordThenShouldNotReturnRespectiveUser() throws UserAlreadyExistsException, UserNotFoundException {
        User savedUser = userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        user1.setEmail("sdfsdf@cfd.cd");
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserByEmailAndPassword(user1.getEmail(), savedUser.getPassword()));
    }

    @Test
    void givenUserIdToDeleteThenShouldReturnDeletedUser() throws UserAlreadyExistsException, UserNotFoundException {
        User savedUser = userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        User deletedUser = userService.deleteUser(savedUser.getId());
        assertNotNull(deletedUser);
        assertEquals(user1.getEmail(), deletedUser.getEmail());
    }

    @Test
    void givenUserIdToDeleteThenShouldNotReturnDeletedUser() throws UserNotFoundException {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteUser(user1.getId()));
    }

    @Test
    public void givenUserToUpdateThenShouldReturnUpdatedUser() throws UserAlreadyExistsException, UserNotFoundException {
        User savedUser = userService.saveUser(user1);
        assertNotNull(savedUser);
        assertEquals(user1.getEmail(), savedUser.getEmail());;
        savedUser.setPassword(user2.getPassword());
        User updatedUser = userService.updateUser(savedUser);
        assertNotNull(savedUser);
        assertEquals(savedUser, updatedUser);
    }

    @Test
    public void givenUserToUpdateWithAlreadyExistingEmailThenShouldNotReturnUpdatedUser() throws UserNotFoundException, UserAlreadyExistsException {
        User savedUser = userService.saveUser(user1);
        userService.saveUser(user2);
        assertNotNull(savedUser);
        assertEquals(user1.getEmail(), savedUser.getEmail());;
        savedUser.setEmail(user2.getEmail());
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.updateUser(savedUser));
    }

    @Test
    public void givenUserToUpdateThenShouldNotReturnUpdatedUser() throws UserAlreadyExistsException, UserNotFoundException {
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUser(user1));
    }

    /******* VALIDATION *****/
    @Test
    void givenValidUserThenReturnRespectiveUser() throws UserAlreadyExistsException {
        assertEquals(user1.getEmail(), userService.saveUser(user1).getEmail());
    }

    @Test
    void givenUserWithInvalidEmailThenThrowsException() throws UserAlreadyExistsException, ConstraintViolationException{
        user1.setEmail("anas");
        assertThrows(ConstraintViolationException.class, () -> userService.saveUser(user1));
    }

    @Test
    void givenUserWithInvalidNameThenThrowsException() throws UserAlreadyExistsException, ConstraintViolationException{
        user1.setName("");
        assertThrows(ConstraintViolationException.class, () -> userService.saveUser(user1));
    }

    @Test
    void givenUserWithInvalidPasswordThenThrowsException() throws UserAlreadyExistsException, ConstraintViolationException{
        user1.setPassword("12345");
        assertThrows(ConstraintViolationException.class, () -> userService.saveUser(user1));
    }
}