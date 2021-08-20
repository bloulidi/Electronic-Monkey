package com.stackroute.userservice.dto;

import com.stackroute.userservice.model.Login;
import com.stackroute.userservice.model.UserDetails;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Class representing a user dto tracked by the application.")
public class UserDto {
    @Id
    @NotNull(message = "ID cannot be null")
    @Min(1)
    @ApiModelProperty(notes = "ID of the user. Must be a positive integer excluding zero", position = 0)
    private int id;

    @NotBlank(message = "Name cannot be empty")
    @ApiModelProperty(notes = "Name of the user", example = "Justin Trudeau", required = true, position = 1)
    private String name;

    @Email(message = "Please provide a valid email address")
    @ApiModelProperty(notes = "Email of the user", example = "justin.trudeau@cgi.com", required = true, position = 2)
    private String email;

    @ApiModelProperty(notes = "Admin boolean value is true if user is an admin. Default: false", example = "true", position = 3)
    private boolean admin = false;

    @Valid
    private Login loginInfo;

    @Valid
    private UserDetails userDetails;
}