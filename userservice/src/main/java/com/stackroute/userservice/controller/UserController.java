package com.stackroute.userservice.controller;

import com.stackroute.userservice.converter.UserConverter;
import com.stackroute.userservice.dto.UserDto;
import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@Api(tags = { com.stackroute.userservice.swagger.SpringFoxConfig.USER_TAG })
public class UserController {
    private UserService userService;
    private UserConverter converter;

    @Autowired
    public UserController(UserService userService, UserConverter converter) {
        this.userService = userService;
        this.converter = converter;
    }

    @PostMapping("/user")
    @ApiOperation("Creates a new user.")
    public ResponseEntity<UserDto> saveUser(@ApiParam("User information for a new user to be created. 409 if already exists.") @Valid @RequestBody UserDto userDto) throws UserAlreadyExistsException {
        return new ResponseEntity<UserDto>(converter.entityToDto(userService.saveUser(converter.dtoToEntity(userDto))), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    @ApiOperation("Returns list of all users in the system.")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<List<UserDto>>(converter.entityToDto(userService.getAllUsers()), HttpStatus.FOUND);
    }

    @GetMapping("/user/{id}")
    @ApiOperation("Returns a specific user by their identifier. 404 if does not exist.")
    public ResponseEntity<UserDto> getUserById(@ApiParam("Id of the user to be obtained. Cannot be empty.") @PathVariable int id) throws UserNotFoundException {
        return new ResponseEntity<UserDto>(converter.entityToDto(userService.getUserById(id)), HttpStatus.FOUND);
    }

    @GetMapping("/user/email/{email}")
    @ApiOperation("Returns a specific user by their email. 404 if does not exist.")
    public ResponseEntity<UserDto> getUserByEmail(@ApiParam("Email of the user to be obtained. Cannot be empty.") @Valid @PathVariable String email) throws UserNotFoundException {
        return new ResponseEntity<UserDto>(converter.entityToDto(userService.getUserByEmail(email)), HttpStatus.FOUND);
    }

    @GetMapping("/users/{name}")
    @ApiOperation("Returns a list of users by their name.")
    public ResponseEntity<List<UserDto>> getUsersByName(@ApiParam("Name of the users to be obtained. Cannot be empty.") @Valid @PathVariable String name) {
        return new ResponseEntity<List<UserDto>>(converter.entityToDto(userService.getUsersByName(name)), HttpStatus.FOUND);
    }

    @GetMapping("/users/admin/{admin}")
    @ApiOperation("Returns a list of users by their name.")
    public ResponseEntity<List<UserDto>> getUsersByAdmin(@ApiParam("Admin boolean value is true if user is an admin.") @PathVariable boolean admin) {
        return new ResponseEntity<List<UserDto>>(converter.entityToDto(userService.getUsersByAdmin(admin)), HttpStatus.FOUND);
    }

    @DeleteMapping("/user/{id}")
    @ApiOperation("Deletes a user from the system. 404 if the person's identifier is not found.")
    public ResponseEntity<UserDto> deleteUserById(@ApiParam("Id of the user to be deleted. Cannot be empty.") @PathVariable int id) throws UserNotFoundException {
        return new ResponseEntity<UserDto>(converter.entityToDto(userService.deleteUser(id)), HttpStatus.OK);
    }

    @PatchMapping("/user")
    @ApiOperation("Updates a new user.")
    public ResponseEntity<UserDto> updateUser(@ApiParam("User information for a user to be updated. 404 if does not exist.") @RequestBody User user) throws UserNotFoundException {
        return new ResponseEntity<UserDto>(converter.entityToDto(userService.updateUser(user)), HttpStatus.OK);
    }
}