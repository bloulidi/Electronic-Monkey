package com.stackroute.orderservice.model;

import com.stackroute.orderservice.exception.StatusConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
@ApiModel(description = "Class representing an order tracked by the application.")
public class Order extends BaseModel {

    @StatusConstraint
    @ApiModelProperty(notes = "Order Status", example = "Pending", required = true, position = 1)
    String status = Status.PENDING.getStatus();

    @Size(min = 1, message = "There must be at least one product in the order")
    @Valid
    @ApiModelProperty(notes = "List of order product Ids", required = true, position = 2)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Min(value = 1, message = "The user Id must be greater or equal than 1")
    @ApiModelProperty(notes = "User Id associated to the order", example = "50", required = true, position = 3)
    private long userId;

    public int getNumberOfProducts() {
        return this.orderProducts.size();
    }

    public float getTotalOrderPrice() {
        float sum = 0F;
        List<OrderProduct> orderProducts = getOrderProducts();
        for (OrderProduct op : orderProducts) {
            if(op != null) {
                sum += op.getTotalPrice();
            }
        }
        return sum;
    }

    public Order(List<OrderProduct> orderProducts, long userId) {
        this.orderProducts = orderProducts;
        this.userId = userId;
    }
}