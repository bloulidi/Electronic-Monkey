package com.stackroute.orderservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order extends BaseModel{

    private String status;

    @Valid
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @NotBlank(message = "Total price cannot be empty")
    @ApiModelProperty(notes = "Total price of the product", example = "999", required = true, position = 3)
    private float totalPrice;

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