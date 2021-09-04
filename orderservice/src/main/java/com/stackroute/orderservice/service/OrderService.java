package com.stackroute.orderservice.service;

import com.stackroute.orderservice.exception.OrderAlreadyExistsException;
import com.stackroute.orderservice.exception.OrderNotFoundException;
import com.stackroute.orderservice.model.Order;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
public interface OrderService {
    Order saveOrder(@Valid Order order) throws OrderAlreadyExistsException;

    Order getOrderById(@NotBlank(message = "ID cannot be empty.") String id) throws OrderNotFoundException;

    Order deleteOrder(@NotBlank(message = "ID cannot be empty.") String id) throws OrderNotFoundException;

    Order updateOrder(@Valid Order order) throws OrderAlreadyExistsException, OrderNotFoundException;

    List<Order> getOrdersByUserId(long userId);

    List<Order> getAllOrders();
}
