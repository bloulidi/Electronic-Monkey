package com.stackroute.userservice.controller;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1")
@Api(tags = { com.stackroute.userservice.swagger.SpringFoxConfig.USER_TAG })
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ApiOperation("Creates a new user.")
    public ResponseEntity<User> saveUser(@ApiParam("User information for a new user to be created. 409 if already exists.") @RequestBody User user) throws UserAlreadyExistsException {
        log.info("Create a new user: " + user.toString());
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    @ApiOperation("Returns list of all users in the system.")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Return list of all users in the system.");
        return new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @ApiOperation("Returns a specific user by their identifier. 404 if does not exist.")
    public ResponseEntity<User> getUserById(@ApiParam("Id of the user to be obtained. Cannot be empty.")  @PathVariable int id) throws UserNotFoundException {
        log.info("Return user with id = " + id);
        return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/users/email/{email}")
    @ApiOperation("Returns a specific user by their email. 404 if does not exist.")
    public ResponseEntity<User> getUserByEmail(@ApiParam("Email of the user to be obtained. Cannot be empty.") @PathVariable String email) throws UserNotFoundException {
        log.info("Return user with email = " + email);
        return new ResponseEntity<User>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/users/name/{name}")
    @ApiOperation("Returns a list of users by their name.")
    public ResponseEntity<List<User>> getUsersByName(@ApiParam("Name of the users to be obtained. Cannot be empty.") @PathVariable String name) {
        log.info("Return users with name = " + name);
        return new ResponseEntity<List<User>>(userService.getUsersByName(name), HttpStatus.OK);
    }

    @GetMapping("/users/admin/{admin}")
    @ApiOperation("Returns a list of users by their name.")
    public ResponseEntity<List<User>> getUsersByAdmin(@ApiParam("Admin boolean value is true if user is an admin.") @PathVariable boolean admin) {
        log.info("Return users with admin = " + admin);
        return new ResponseEntity<List<User>>(userService.getUsersByAdmin(admin), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @ApiOperation("Deletes a user from the system. 404 if the person's identifier is not found.")
    public ResponseEntity<User> deleteUser(@ApiParam("Id of the user to be deleted. Cannot be empty.") @PathVariable int id) throws UserNotFoundException {
        log.info("Delete user with id = " + id);
        return new ResponseEntity<User>(userService.deleteUser(id), HttpStatus.OK);
    }

    @PatchMapping("/users")
    @ApiOperation("Updates a new user.")
    public ResponseEntity<User> updateUser(@ApiParam("User information for a user to be updated. 404 if does not exist.") @RequestBody User user) throws UserNotFoundException {
        log.info("Update user: " + user.toString());
        return new ResponseEntity<User>(userService.updateUser(user), HttpStatus.OK);
    }
}