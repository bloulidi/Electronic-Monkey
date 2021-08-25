package com.stackroute.catalog.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@ApiModel(description = "Class representing a product tracked by the application.")
public class Product {

    @Id
    @ApiModelProperty(notes = "ID of the product. Must be a positive integer excluding zero", required = true, position = 0)
    private long id;

    @NotBlank(message = "Code cannot be empty.")
    @Indexed(unique = true)
    @ApiModelProperty(notes = "Code of the product", example = "38MT7H2", required = true, position = 1)
    private String code;

    @NotBlank(message = "Name cannot be empty.")
    @ApiModelProperty(notes = "Name of the product", example = "Tea", required = true, position = 2)
    private String name;

    @ApiModelProperty(notes = "Description of the product", example = "A green cup", position = 3)
    private String description = "";

    @ApiModelProperty(notes = "Category of the product", example = "Cold Drinks", position = 4)
    private String category = "";

    @NotNull(message = "Price cannot be null.")
    @ApiModelProperty(notes = "Price of the product", example = "19.99", required = true, position = 5)
    private double price;
}