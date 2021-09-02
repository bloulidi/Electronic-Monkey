package com.stackroute.orderservice.model;

import com.stackroute.orderservice.model.product.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {

    @Min(1)
    @ApiModelProperty(notes = "Quantity of order products", example = "1", required = true, position = 1)
    private int quantity = 1;

    @Valid
    @ApiModelProperty(notes = "Order product information", required = true, position = 2)
    private Product product;

    public float getTotalPrice() {
        return product.getPrice() * getQuantity();
    }
}
