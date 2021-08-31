package com.stackroute.orderservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
@ApiModel(description = "Class representing an order tracked by the application.")
public class Order extends BaseModel{

    @NotBlank(message = "Status cannot be empty")
    @ApiModelProperty(notes = "Order Status", example = "999", required = true, position = 1)
    private String status;

    @Valid
    @ApiModelProperty(notes = "List of order products", required = true, position = 2)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @ApiModelProperty(notes = "Total price of the product", example = "999", position = 3)
    private float totalPrice;

    @ApiModelProperty(notes = "User Id associated to the order", example = "50", required = true, position = 4)
    private long userId;

    @Transient //Not stored in the db
    public float getTotalOrderPrice() {
        float sum = 0F;
        List<OrderProduct> orderProducts = getOrderProducts();
        for (OrderProduct op : orderProducts) {
            sum += op.getTotalPrice();
        }
        return sum;
    }

    @Transient
    public int getNumberOfProducts() {
        return this.orderProducts.size();
    }
}