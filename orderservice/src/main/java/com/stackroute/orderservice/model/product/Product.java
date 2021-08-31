package com.stackroute.orderservice.model.product;

import com.stackroute.orderservice.exception.CategoryConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Class representing a product.")
public class Product {

    @NotBlank(message = "Title cannot be empty.")
    @ApiModelProperty(notes = "Title of the product", example = "DELL Laptop", required = true, position = 1)
    private String title;

    @ApiModelProperty(notes = "Description of the product", example = "i7-7700 CPU, GTX 1650", position = 2)
    private String description = "";

    @CategoryConstraint
    @ApiModelProperty(notes = "Category of the product", example = "Computers", required = true, position = 3)
    private String category;

    @Digits(integer = 9, fraction = 2, message = "Please provide a number with a maximum of only 2 decimal places and 9 integer digits")
    @Min(0) @Max(999999999)
    @ApiModelProperty(notes = "Price of the product", example = "19.99", required = true, position = 4)
    private float price;

    @ApiModelProperty(notes = "Image of the product", position = 5)
    private Photo photo;

    public Product(String title, String description, String category, float price) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public Product(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }
}