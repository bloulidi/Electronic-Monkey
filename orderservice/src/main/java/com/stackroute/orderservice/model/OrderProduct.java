package com.stackroute.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stackroute.orderservice.model.product.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orderProducts")
public class OrderProduct {

    @Id
    @JsonIgnore //We don't want to serialize Order part of the primary key since it'd be redundant.
    private OrderProductPK pk;

    @ApiModelProperty(notes = "Quantity of order products", required = true, position = 1)
    private int quantity = 1;

    @ApiModelProperty(notes = "Order product information", required = true, position = 2)
    private Product product;

    @Transient
    public float getTotalPrice() {
        return product.getPrice() * getQuantity();
    }
}
