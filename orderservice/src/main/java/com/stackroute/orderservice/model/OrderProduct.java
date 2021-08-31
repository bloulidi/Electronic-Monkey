package com.stackroute.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stackroute.orderservice.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orderProducts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {

    @Id
    @JsonIgnore //We don't want to serialize Order part of the primary key since it'd be redundant.
    private OrderProductPK pk;

    private int quantity = 1;

    private Product product;

    @Transient
    public float getTotalPrice() {
        return product.getPrice() * getQuantity();
    }
}
