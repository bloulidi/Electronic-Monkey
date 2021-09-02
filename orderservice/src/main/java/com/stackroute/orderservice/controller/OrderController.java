package com.stackroute.orderservice.controller;

import com.stackroute.orderservice.exception.OrderAlreadyExistsException;
import com.stackroute.orderservice.exception.OrderNotFoundException;
import com.stackroute.orderservice.model.Order;
import com.stackroute.orderservice.model.Status;
import com.stackroute.orderservice.service.OrderService;
import com.stackroute.orderservice.swagger.SpringFoxConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/orders")
@Api(tags = { SpringFoxConfig.ORDER_TAG })
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ApiOperation("Creates a new order.")
    public ResponseEntity<Order> saveOrder(@ApiParam("New order is created. 409 if already exists.") @RequestBody Order order)  throws OrderAlreadyExistsException  {
        log.info("Create a new order, id: " + order.getId());
        order.setStatus(Status.DONE.getStatus());
        return new ResponseEntity<Order>(orderService.saveOrder(order), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation("Returns list of all orders in the system.")
    public ResponseEntity<List<Order>> getAllOrders() {
        log.info("Return list of all orders in the system.");
        return new ResponseEntity<List<Order>>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiOperation("Returns a specific order by their identifier. 404 if does not exist.")
    public ResponseEntity<Order> getOrderById(@ApiParam("Id of the order to be obtained. Cannot be empty.")  @PathVariable String id) throws OrderNotFoundException {
        log.info("Return order with id = " + id);
        return new ResponseEntity<Order>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    @ApiOperation("Returns a list of orders by their userId. 404 if does not exist.")
    public ResponseEntity<List<Order>> getOrderByUserId(@ApiParam("Code of the order to be obtained. Cannot be empty.") @PathVariable long userId) throws OrderNotFoundException {
        log.info("Return orders with userId = " + userId);
        return new ResponseEntity<List<Order>>(orderService.getOrdersByUserId(userId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletes a order from the system. 404 if the person's identifier is not found.")
    public ResponseEntity<Order> deleteOrder(@ApiParam("Id of the order to be deleted. Cannot be empty.") @PathVariable String id) throws OrderNotFoundException {
        log.info("Delete order with id = " + id);
        return new ResponseEntity<Order>(orderService.deleteOrder(id), HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation("Updates a new order.")
    public ResponseEntity<Order> updateOrder(@ApiParam("Order information for a order to be updated. 404 if does not exist.") @RequestBody Order order) throws OrderNotFoundException {
        log.info("Update order, id: " + order.getId());
        return new ResponseEntity<Order>(orderService.updateOrder(order), HttpStatus.OK);
    }
}