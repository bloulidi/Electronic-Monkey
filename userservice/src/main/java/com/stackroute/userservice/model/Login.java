package com.stackroute.userservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@ApiModel(description = "User login information")
public class Login {

    @NotBlank(message = "Username cannot be empty")
    @ApiModelProperty(notes = "Username of the user", example = "justin.trudeau@cgi.com", required = true, position = 0)
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must consist of at least 6 characters.")
    @ApiModelProperty(notes = "Password of the user", example = "Password123.", required = true, position = 1)
    private String password;
}