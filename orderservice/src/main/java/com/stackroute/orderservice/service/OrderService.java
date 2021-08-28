package com.stackroute.orderservice.service;
import com.stackroute.orderservice.model.Order;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface OrderService {
    Order saveOrder(@Valid Order user);
}