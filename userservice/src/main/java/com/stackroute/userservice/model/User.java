package com.stackroute.userservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Class representing a user tracked by the application.")
public class User {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "ID of the user. Must be a positive integer excluding zero", required = true, position = 0)
    private long id;

    @NotBlank(message = "Name cannot be empty")
    @ApiModelProperty(notes = "Name of the user", example = "Justin Trudeau", required = true, position = 1)
    private String name;

    @Email(message = "Please provide a valid email address")
    @Column(unique = true)
    @ApiModelProperty(notes = "Email of the user", example = "justin.trudeau@cgi.com", required = true, position = 2)
    private String email;

    @NotNull(message = "admin must be a boolean value: true | false")
    @ApiModelProperty(notes = "Admin boolean value is true if user is an admin. Default: false", example = "true", position = 3)
    private boolean admin;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must consist of at least 6 characters.")
    @ApiModelProperty(notes = "Password of the user", example = "Password123.", required = true, position = 4)
    private String password;
}