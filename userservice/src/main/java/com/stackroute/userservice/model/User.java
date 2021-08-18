package com.stackroute.userservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Class representing a user tracked by the application.")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Id of the user", position = 0)
    private int id;

    @NotBlank(message = "Name cannot be empty")
    @ApiModelProperty(notes = "Name of the user", example = "Justin Trudeau", required = true, position = 1)
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp ="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @ApiModelProperty(notes = "Email of the user", example = "justin.trudeau@cgi.com", required = true, position = 2)
    private String email;

    @Embedded
    private Login loginInfo;

    @Embedded
    private UserDetails userDetails;
}
