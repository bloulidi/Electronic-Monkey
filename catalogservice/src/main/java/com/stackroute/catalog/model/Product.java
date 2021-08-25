package com.stackroute.catalog.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
@ApiModel(description = "Class representing a product tracked by the application.")
public class Product extends BaseModel {

    @NotBlank(message = "Code cannot be empty.")
    @Indexed(unique = true)
    @ApiModelProperty(notes = "Code of the product", example = "38MT7H2", required = true, position = 1)
    private String code;

    @NotBlank(message = "Title cannot be empty.")
    @ApiModelProperty(notes = "Title of the product", example = "DELL Laptop", required = true, position = 2)
    private String title;

    @ApiModelProperty(notes = "Description of the product", example = "i7-7700 CPU, GTX 1650", position = 3)
    private String description = "";

    @Indexed
    @Field(targetType = FieldType.STRING)
    @ApiModelProperty(notes = "Category of the product", example = "Computers", required = true, position = 4)
    private Category category;

    @NotNull(message = "Price cannot be null.")
    @ApiModelProperty(notes = "Price of the product", example = "19.99", required = true, position = 5)
    private double price;
}