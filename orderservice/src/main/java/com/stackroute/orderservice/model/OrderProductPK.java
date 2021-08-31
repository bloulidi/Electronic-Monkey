package com.stackroute.orderservice.model;

import com.stackroute.orderservice.model.product.Product;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
public class OrderProductPK implements Serializable {
    private String orderId;
    private String productId;
}
