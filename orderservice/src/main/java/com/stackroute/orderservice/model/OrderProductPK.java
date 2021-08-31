package com.stackroute.orderservice.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrderProductPK implements Serializable {
    private String orderId;
    private String productId;
}
