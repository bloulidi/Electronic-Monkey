package com.stackroute.userservice.dto;

import com.stackroute.userservice.model.Login;
import com.stackroute.userservice.model.UserDetails;
import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String name;
    private String email;
    private Login loginInfo;
    private UserDetails userDetails;
}
