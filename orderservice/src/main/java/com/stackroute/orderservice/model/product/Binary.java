package com.stackroute.orderservice.model.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Class representing a binary file data used for Photo.")
public class Binary {
    @ApiModelProperty(notes = "Type of the binary")
    private short type;
    @ApiModelProperty(notes = "Data of the binary")
    private String data;
}
