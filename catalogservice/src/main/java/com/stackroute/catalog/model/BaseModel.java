package com.stackroute.catalog.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Setter
@Getter
public abstract class BaseModel {

    @ApiModelProperty(notes = "Date of product creation. Leave empty as it's set automatically.")
    protected Date createdAt;
    @ApiModelProperty(notes = "Date of product update. Leave empty as it's set automatically.")
    protected Date updatedAt;
    @Id
    @ApiModelProperty(notes = "Id of the product. Auto-generated if not manually set")
    private String id;
}