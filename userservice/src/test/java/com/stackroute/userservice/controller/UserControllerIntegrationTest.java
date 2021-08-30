package com.stackroute.userservice.controller;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.StatusAssertions;

import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserControllerIntegrationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;


    private User user1, user2, user3;
    private List<User> userList;
    private List<String> usersEmail, savedUsersEmail;
    private Logger logger;

    @BeforeEach
    void setUp() {
        logger = LoggerFactory.getLogger(UserController.class);
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
        User savedUser = userController.saveUser(user1).getBody();
        assertNotNull(savedUser);
        assertEquals(user1.getEmail(), savedUser.getEmail());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenUserToSaveThenShouldNotReturnSavedUser() throws UserAlreadyExistsException {
        userController.saveUser(user1);
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userController.saveUser(user1));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenGetAllUsersThenShouldReturnListOfAllUsers() throws UserAlreadyExistsException {
        User savedUser1 = userController.saveUser(user1).getBody();
        User savedUser2 = userController.saveUser(user2).getBody();
        User savedUser3 = userController.saveUser(user3).getBody();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        List<User> users = userController.getAllUsers().getBody();
        for (User user : userList) { usersEmail.add(user.getEmail()); }
        for (User user : users) { savedUsersEmail.add(user.getEmail()); }
        assertNotNull(users);
        assertEquals(usersEmail, savedUsersEmail);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenGetAllUsersByNameThenShouldReturnListOfAllRespectiveUsers() throws UserAlreadyExistsException {
        userList.add(user2);
        userList.add(user3);
        userController.saveUser(user1);
        userController.saveUser(user2);
        userController.saveUser(user3);
        List<User> users = userController.getUsersByName(user2.getName()).getBody();
        for (User user : userList) { usersEmail.add(user.getEmail()); }
        for (User user : users) { savedUsersEmail.add(user.getEmail()); }
        assertNotNull(users);
        assertEquals(usersEmail, savedUsersEmail);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenGetAllUsersByAdminThenShouldReturnListOfAllAdminUsers() throws UserAlreadyExistsException {
        userList.add(user1);
        userList.add(user3);
        userController.saveUser(user1);
        //userController.saveUser(user2);
        userController.saveUser(user3);
        List<User> users = userController.getUsersByAdmin(user1.isAdmin()).getBody();
        for (User user : userList) { usersEmail.add(user.getEmail()); }
        for (User user : users) { savedUsersEmail.add(user.getEmail()); }
        assertNotNull(users);
        assertEquals(usersEmail, savedUsersEmail);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenUserIdThenShouldReturnRespectiveUser() throws UserAlreadyExistsException, UserNotFoundException {
        User savedUser = userController.saveUser(user1).getBody();
        userController.saveUser(user2);
        userController.saveUser(user3);
        User getUser = userController.getUserById(savedUser.getId()).getBody();
        assertNotNull(getUser);
        assertEquals(user1.getEmail(), getUser.getEmail());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenUserIdThenShouldNotReturnRespectiveUser() throws UserNotFoundException {
        Assertions.assertThrows(UserNotFoundException.class, () -> userController.getUserById(user1.getId()));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenUserEmailThenShouldReturnRespectiveUser() throws UserAlreadyExistsException, UserNotFoundException {
        User savedUser = userController.saveUser(user1).getBody();
        userController.saveUser(user2);
        userController.saveUser(user3);
        User getUser = userController.getUserByEmail(savedUser.getEmail()).getBody();
        assertNotNull(getUser);
        assertEquals(user1.getEmail(), getUser.getEmail());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenUserEmailThenShouldNotReturnRespectiveUser() throws UserNotFoundException {
        Assertions.assertThrows(UserNotFoundException.class, () -> userController.getUserByEmail(user1.getEmail()));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenUserIdToDeleteThenShouldReturnDeletedUser() throws UserAlreadyExistsException, UserNotFoundException {
        User savedUser = userController.saveUser(user1).getBody();
        userController.saveUser(user2);
        userController.saveUser(user3);
        User deletedUser = userController.deleteUser(savedUser.getId()).getBody();
        assertNotNull(deletedUser);
        assertEquals(user1.getEmail(), deletedUser.getEmail());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenUserIdToDeleteThenShouldNotReturnDeletedUser() throws UserNotFoundException {
        Assertions.assertThrows(UserNotFoundException.class, () -> userController.deleteUser(user1.getId()));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenUserToUpdateThenShouldReturnUpdatedUser() throws UserAlreadyExistsException, UserNotFoundException {
        User savedUser = userController.saveUser(user1).getBody();
        assertNotNull(savedUser);
        assertEquals(user1.getEmail(), savedUser.getEmail());;
        savedUser.setPassword(user2.getPassword());
        User updatedUser = userController.updateUser(savedUser).getBody();
        assertNotNull(savedUser);
        assertEquals(savedUser, updatedUser);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenUserToUpdateWithAlreadyExistingEmailThenShouldNotReturnUpdatedUser() throws UserNotFoundException, UserAlreadyExistsException {
        User savedUser = userController.saveUser(user1).getBody();
        userController.saveUser(user2);
        assertNotNull(savedUser);
        assertEquals(user1.getEmail(), savedUser.getEmail());;
        savedUser.setEmail(user2.getEmail());
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userController.updateUser(savedUser).getBody());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenUserToUpdateThenShouldNotReturnUpdatedUser() throws UserNotFoundException, UserAlreadyExistsException {
        Assertions.assertThrows(UserNotFoundException.class, () -> userController.updateUser(user1));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

/*    @Test
    public void givenValidUserToLoginThenShouldReturnOkStatus() throws UserAlreadyExistsException, UserNotFoundException {
        User savedUser = userController.saveUser(user1).getBody();
        assertEquals(200, userController.loginUser(user1).getStatusCode().value());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenInValidUserToLoginThenThrowsError() throws UserNotFoundException {
        assertEquals(409, userController.loginUser(user1).getStatusCode().value());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }
*/
    /******* VALIDATION *****/
    @Test
    void givenValidUserThenReturnRespectiveUser() throws UserAlreadyExistsException {
        User savedUser = userController.saveUser(user1).getBody();
        assertEquals(user1.getEmail(), savedUser.getEmail());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenUserWithInvalidEmailThenThrowsException() throws UserAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            user1.setEmail("anas");
            userController.saveUser(user1);
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenUserWithInvalidNameThenThrowsException() throws UserAlreadyExistsException, ConstraintViolationException{
        assertThrows(ConstraintViolationException.class, () -> {
            user1.setName("");
            userController.saveUser(user1);
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenUserWithInvalidPasswordThenThrowsException() throws UserAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            user1.setPassword("12345");
            userController.saveUser(user1);
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }
}