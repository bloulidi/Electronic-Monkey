package com.stackroute.orderservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Order {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "ID of the order. Must be a positive integer excluding zero", required = true, position = 0)
    private int id;

    @NotBlank(message = "image cannot be empty")
    @ApiModelProperty(notes = "Image of the product", required = true, position = 1)
    private String image;

    @NotBlank(message = "Name cannot be empty")
    @ApiModelProperty(notes = "Name of the product", example = "Iphone X", required = true, position = 2)
    private String name;

    @NotBlank(message = "Price cannot be empty")
    @ApiModelProperty(notes = "Price of the product", example = "999", required = true, position = 3)
    private long price;

    @NotBlank(message = "Quantity cannot be empty")
    @ApiModelProperty(notes = "Quantity of the product", example = "2", required = true, position = 4)
    private int quantity;

    @NotBlank(message = "Total cannot be empty")
    @ApiModelProperty(notes = "Total of the product", example = "999", required = true, position = 5)
    private int total;
}