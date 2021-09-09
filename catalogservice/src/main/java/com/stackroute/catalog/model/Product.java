package com.stackroute.catalog.model;

import com.stackroute.catalog.exception.CategoryConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
@ApiModel(description = "Class representing a product tracked by the application.")
public class Product extends BaseModel {

    @NotBlank(message = "Title cannot be empty")
    @ApiModelProperty(notes = "Title of the product", example = "DELL Laptop", required = true, position = 1)
    private String title;

    @ApiModelProperty(notes = "Description of the product", example = "i7-7700 CPU, GTX 1650", position = 2)
    private String description = "";

    @CategoryConstraint
    @Indexed
    @ApiModelProperty(notes = "Category of the product", example = "Computers", required = true, position = 3)
    private String category;

    @Digits(integer = 9, fraction = 2, message = "Please provide a number with a maximum of only 2 decimal places and 9 integer digits")
    @Min(value = 1, message = "The minimum price value should be 1")
    @Max(value = 999999999, message = "The maximum price value is 999999999")
    @ApiModelProperty(notes = "Price of the product", example = "19.99", required = true, position = 4)
    private float price;

    @ApiModelProperty(notes = "Image of the product", position = 5)
    private Photo photo;

    @Min(value = 1, message = "The minimum user Id is 1")
    @ApiModelProperty(notes = "User associated to the product", example = "1", required = true, position = 6)
    private long userId;

    @ApiModelProperty(notes = "Hidden product", position = 7)
    private boolean hidden;
}