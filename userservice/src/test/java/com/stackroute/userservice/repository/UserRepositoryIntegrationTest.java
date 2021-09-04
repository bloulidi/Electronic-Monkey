package com.stackroute.userservice.repository;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    private User user1, user2, user3;
    private List<User> userList;
    private List<String> usersEmail, savedUsersEmail;

    @BeforeEach
    void setUp() {
        user1 = new User(1, "Anas", "anas@cgi.com", true, "password");
        user2 = new User(2, "Justin", "justin@hotmail.com", false, "password");
        user3 = new User(3, "Justin", "justin@cgi.com", true, "password");
        userList = new ArrayList<User>();
        savedUsersEmail = new ArrayList<>();
        usersEmail = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() {
        user1 = user2 = user3 = null;
        userList = null;
        usersEmail = savedUsersEmail = null;
    }

    @Test
    public void givenUserToSaveThenShouldReturnSavedUser() throws UserAlreadyExistsException {
        User savedUser = userRepository.save(user1);
        assertNotNull(savedUser);
        assertEquals(user1.getEmail(), savedUser.getEmail());
        userRepository.deleteAll();
    }

    @Test
    public void givenGetAllUsersThenShouldReturnListOfAllUsers() {
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        List<User> users = (List<User>) userRepository.findAll();
        for (User user : userList) {
            usersEmail.add(user.getEmail());
        }
        for (User user : users) {
            savedUsersEmail.add(user.getEmail());
        }
        assertNotNull(users);
        assertEquals(usersEmail, savedUsersEmail);
        userRepository.deleteAll();
    }

    @Test
    public void givenGetAllUsersByNameThenShouldReturnListOfAllRespectiveUsers() {
        userList.add(user2);
        userList.add(user3);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        List<User> users = userRepository.findByName(user2.getName());
        for (User user : userList) {
            usersEmail.add(user.getEmail());
        }
        for (User user : users) {
            savedUsersEmail.add(user.getEmail());
        }
        assertNotNull(users);
        assertEquals(usersEmail, savedUsersEmail);
        userRepository.deleteAll();
    }

    @Test
    public void givenGetAllUsersByAdminThenShouldReturnListOfAllAdminUsers() {
        userList.add(user1);
        userList.add(user3);
        userRepository.save(user1);
        //userRepository.save(user2);
        userRepository.save(user3);
        List<User> users = userRepository.findByAdmin(user1.isAdmin());
        for (User user : userList) {
            usersEmail.add(user.getEmail());
        }
        for (User user : users) {
            savedUsersEmail.add(user.getEmail());
        }
        assertNotNull(users);
        assertEquals(usersEmail, savedUsersEmail);
        userRepository.deleteAll();
    }

    @Test
    public void givenUserIdThenShouldReturnRespectiveUser() throws UserNotFoundException {
        User savedUser = userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        User getUser = userRepository.findById(savedUser.getId()).get();
        assertNotNull(getUser);
        assertEquals(user1.getEmail(), getUser.getEmail());
        userRepository.deleteAll();
    }

    @Test
    public void givenUserEmailThenShouldReturnRespectiveUser() throws UserNotFoundException {
        User savedUser = userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        User getUser = userRepository.findByEmail(savedUser.getEmail());
        assertNotNull(getUser);
        assertEquals(user1.getEmail(), getUser.getEmail());
        userRepository.deleteAll();
    }

    @Test
    void givenUserIdToDeleteThenShouldReturnDeletedUser() throws UserNotFoundException {
        User savedUser = userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.deleteById(savedUser.getId());
        userList.add(user2);
        userList.add(user3);
        List<User> users = (List<User>) userRepository.findAll();
        for (User user : userList) {
            usersEmail.add(user.getEmail());
        }
        for (User user : users) {
            savedUsersEmail.add(user.getEmail());
        }
        assertNotNull(users);
        assertEquals(usersEmail, savedUsersEmail);
        userRepository.deleteAll();
    }

    @Test
    public void givenUserToUpdateThenShouldReturnUpdatedUser() {
        User savedUser = userRepository.save(user1);
        assertNotNull(savedUser);
        assertEquals(user1.getEmail(), savedUser.getEmail());
        ;
        savedUser.setPassword(user2.getPassword());
        assertEquals(true, userRepository.existsById(savedUser.getId()));
        User updatedUser = userRepository.save(savedUser);
        assertNotNull(savedUser);
        assertEquals(savedUser, updatedUser);
        userRepository.deleteAll();
    }

    /******* VALIDATION *****/
    @Test
    void givenValidUserThenReturnRespectiveUser() {
        assertEquals(user1.getEmail(), userRepository.save(user1).getEmail());
    }

    @Test
    void givenUserWithInvalidEmailThenThrowsException() {
        assertThrows(ConstraintViolationException.class, () -> {
            user1.setEmail("anas");
            userRepository.save(user1);
            entityManager.flush();
            entityManager.clear();
        });
    }

    @Test
    void givenUserWithInvalidPasswordThenThrowsException() {
        assertThrows(ConstraintViolationException.class, () -> {
            user1.setPassword("12345");
            userRepository.save(user1);
            entityManager.flush();
            entityManager.clear();
        });
    }
}