package com.stackroute.catalog.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Class representing a photo associated to products.")
public class Photo {
    @ApiModelProperty(notes = "Title of the photo")
    private String title;
    @ApiModelProperty(notes = "Type of the photo")
    private String type;
    @ApiModelProperty(notes = "Image data of the photo")
    private String image;
}