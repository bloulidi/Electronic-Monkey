package com.stackroute.catalog.model;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
public abstract class BaseModel {

    @Id
    @ApiModelProperty(notes = "Id of the product. Auto-generated if not manually set")
    private String id;

    @ApiModelProperty(notes = "Date of product creation. Leave empty as it's set automatically.")
    protected Date createdAt;

    @ApiModelProperty(notes = "Date of product update. Leave empty as it's set automatically.")
    protected Date updatedAt;
}