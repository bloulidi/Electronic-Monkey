package com.stackroute.orderservice.controller;

import com.stackroute.orderservice.model.Order;
import com.stackroute.orderservice.service.OrderService;
import com.stackroute.orderservice.swagger.SpringFoxConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1")
@Api(tags = { SpringFoxConfig.ORDER_TAG })
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    @ApiOperation("Creates a new order.")
    public ResponseEntity<Order> saveOrder(@ApiParam("New Order is created.") @RequestBody Order order) {
        log.info("Create a new user: " + order.toString());
        return new ResponseEntity<Order>(orderService.saveOrder(order), HttpStatus.CREATED);
    }
}