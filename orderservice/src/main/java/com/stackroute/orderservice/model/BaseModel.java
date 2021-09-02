package com.stackroute.orderservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Setter
@Getter
public abstract class BaseModel {

    @Id
    @ApiModelProperty(notes = "Id. Auto-generated if not manually set")
    private String id;

    @ApiModelProperty(notes = "Date created. Leave empty as it's set automatically.")
    protected Date createdAt;

    @ApiModelProperty(notes = "Date updated. Leave empty as it's set automatically.")
    protected Date updatedAt;
}