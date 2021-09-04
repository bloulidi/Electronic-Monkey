package com.stackroute.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.userservice.exception.GlobalExceptionHandler;
import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;
import com.stackroute.userservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserService userService;
    private MockMvc mockMvc;
    @InjectMocks
    private UserController userController;

    private User user, user1, user2;
    private List<User> userList;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        initMocks(this);
        mockMvc = standaloneSetup(userController).setControllerAdvice(new GlobalExceptionHandler()).build();
        user = new User(1, "Anas", "anas@cgi", true, "password");
        user1 = new User(2, "Justin", "justin@cgi.com", false, "password1");
        user2 = new User(2, "Anas", "anas.d@cgi.com", false, "password");
        userList = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        user = user1 = user2 = null;
        userList = null;
    }

    @Test
    void givenUserToSaveThenShouldReturnSavedUser() throws UserAlreadyExistsException, Exception {
        when(userService.saveUser(any())).thenReturn(user);
        mockMvc.perform(post("/api/v1/users/signup").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(userService).saveUser(any());
        verify(userService, times(1)).saveUser(any());
    }

    @Test
    void givenUserToSaveThenShouldNotReturnSavedUser() throws UserAlreadyExistsException, Exception {
        when(userService.saveUser((User) any())).thenThrow(UserAlreadyExistsException.class);
        mockMvc.perform(post("/api/v1/users/signup").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(userService).saveUser(any());
        verify(userService, times(1)).saveUser(any());
    }

    @Test
    void givenGetAllUsersThenShouldReturnListOfAllUsers() throws Exception {
        userList.add(user);
        userList.add(user1);
        userList.add(user2);
        when(userService.getAllUsers()).thenReturn(userList);
        mockMvc.perform(get("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService).getAllUsers();
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void givenUserIdThenShouldReturnRespectiveUser() throws Exception, UserNotFoundException {
        when(userService.getUserById(user.getId())).thenReturn(user);
        mockMvc.perform(get("/api/v1/users/" + user.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService).getUserById(anyLong());
        verify(userService, times(1)).getUserById(anyLong());
    }

    @Test
    void givenUserIdToDeleteThenShouldReturnDeletedUser() throws UserNotFoundException, Exception {
        when(userService.deleteUser(user.getId())).thenReturn(user);
        mockMvc.perform(delete("/api/v1/users/" + user.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService).deleteUser(anyLong());
        verify(userService, times(1)).deleteUser(anyLong());
    }

    @Test
    void givenUserIdToDeleteThenShouldNotReturnDeletedUser() throws UserNotFoundException, Exception {
        when(userService.deleteUser(user.getId())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(delete("/api/v1/users/" + user.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(userService).deleteUser(anyLong());
        verify(userService, times(1)).deleteUser(anyLong());
    }

    @Test
    public void givenUserToUpdateThenShouldReturnUpdatedUser() throws UserAlreadyExistsException, UserNotFoundException, Exception {
        when(userService.updateUser(any())).thenReturn(user);
        mockMvc.perform(patch("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService).updateUser(any());
        verify(userService, times(1)).updateUser(any());
    }

    @Test
    public void givenUserToUpdateThenShouldNotReturnUpdatedUser() throws UserAlreadyExistsException, UserNotFoundException, Exception {
        when(userService.updateUser(any())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(patch("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(userService).updateUser(any());
        verify(userService, times(1)).updateUser(any());
    }

    @Test
    public void givenGetAllUsersByNameThenShouldReturnListOfAllRespectiveUsers() throws Exception {
        userList.add(user);
        userList.add(user2);
        when(userService.getUsersByName(user.getName())).thenReturn(userList);
        mockMvc.perform(get("/api/v1/users/name/" + user.getName()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService).getUsersByName(anyString());
        verify(userService, times(1)).getUsersByName(anyString());
    }

    @Test
    public void givenGetUserByEmailThenShouldReturnRespectiveUser() throws Exception {
        userList.add(user);
        userList.add(user2);
        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        mockMvc.perform(get("/api/v1/users/email/" + user.getEmail()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService).getUserByEmail(anyString());
        verify(userService, times(1)).getUserByEmail(anyString());
    }

    @Test
    public void givenGetAllUsersByAdminThenShouldReturnListOfAllAdminUsers() throws Exception {
        userList.add(user);
        when(userService.getUsersByAdmin(user.isAdmin())).thenReturn(userList);
        mockMvc.perform(get("/api/v1/users/admin/" + user.isAdmin()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userService).getUsersByAdmin(anyBoolean());
        verify(userService, times(1)).getUsersByAdmin(anyBoolean());
    }
}
