package com.stackroute.userservice.dto;

import com.stackroute.userservice.model.Login;
import com.stackroute.userservice.model.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull(message = "ID cannot be null")
    private int id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @Email(message = "Please provide a valid email address")
    private String email;
    @Valid
    private Login loginInfo;
    @Valid
    private UserDetails userDetails;
}
