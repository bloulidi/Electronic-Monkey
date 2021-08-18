package com.stackroute.userservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserDetails {

    @Pattern(regexp ="^(?:m|M|male|Male|f|F|female|Female)$")
    @ApiModelProperty(notes = "Gender of the user", example = "male", position = 0)
    private String gender;

    @ApiModelProperty(notes = "Birthdate of the user. Format: yyyy-mm-dd", example = "1996-12-15",  position = 1)
    private Date birthdate;

    @ApiModelProperty(notes = "Address of the user.", example = "1350 René-Lévesque Blvd W, Montreal, Quebec H3G 1T4",  position = 2)
    private String address;
}
