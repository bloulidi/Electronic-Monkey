package com.stackroute.orderservice.controller;

import com.stackroute.orderservice.exception.OrderAlreadyExistsException;
import com.stackroute.orderservice.exception.OrderNotFoundException;
import com.stackroute.orderservice.model.Order;
import com.stackroute.orderservice.model.OrderProduct;
import com.stackroute.orderservice.model.product.Category;
import com.stackroute.orderservice.model.product.Photo;
import com.stackroute.orderservice.model.product.Product;
import com.stackroute.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderControllerIntegrationTest {

    @Autowired
    private OrderController orderController;

    @Autowired
    private OrderRepository orderRepository;

    private Order order1, order2, order3;
    private Product product1, product2, product3;
    private OrderProduct orderProduct1, orderProduct2, orderProduct3;
    private List<OrderProduct> orderProductList;
    private List<Order> orderList;
    private Logger logger;

    @BeforeEach
    void setUp() {
        logger = LoggerFactory.getLogger(OrderController.class);
        product1 = new Product("1", "Dell XPS 15", "A laptop", Category.COMPUTERS.getCategory(), 800.5F, new Photo());
        orderProduct1 = new OrderProduct(2, product1);
        product2 = new Product("2", "Apple iPhone 12", "A phone", Category.PHONES.getCategory(), 1000.99F, new Photo());
        orderProduct1 = new OrderProduct(3, product2);
        product3 = new Product("3", "Apple charger", "A charger", Category.ACCESSORIES.getCategory(), 20, new Photo());
        orderProduct1 = new OrderProduct(4, product3);
        orderProductList = new ArrayList<OrderProduct>();
        orderProductList.add(orderProduct1);
        orderProductList.add(orderProduct2);
        orderProductList.add(orderProduct3);
        order1 = new Order(orderProductList, 1);
        order1.setId("1");
        order2 = new Order(orderProductList, 1);
        order2.setId("2");
        order3 = new Order(orderProductList, 2);
        order3.setId("3");
        orderList = new ArrayList<Order>();
    }

    @AfterEach
    public void tearDown() {
        orderRepository.deleteAll();
        order1 = order2 = order3 = null;
        orderProduct1 = orderProduct2 = orderProduct3 = null;
        product1 = product2 = product3 = null;
        orderProductList = null;
        orderList = null;
    }

    @Test
    public void givenOrderToSaveThenShouldReturnSavedOrder() throws OrderAlreadyExistsException, IOException {
        Order savedOrder = orderController.saveOrder(order1).getBody();
        assertNotNull(savedOrder);
        assertEquals(order1.getId(), savedOrder.getId());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenOrderToSaveThenShouldNotReturnSavedOrder() throws OrderAlreadyExistsException {
        orderController.saveOrder(order1);
        Assertions.assertThrows(OrderAlreadyExistsException.class, () -> orderController.saveOrder(order1));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenGetAllOrdersThenShouldReturnListOfAllOrders() throws OrderAlreadyExistsException {
        orderController.saveOrder(order1).getBody();
        orderController.saveOrder(order2).getBody();
        orderController.saveOrder(order3).getBody();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        List<Order> orders = orderController.getAllOrders().getBody();
        assertNotNull(orders);
        assertEquals(orderList, orders);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenOrderIdThenShouldReturnRespectiveOrder() throws OrderAlreadyExistsException, OrderNotFoundException {
        Order savedOrder = orderController.saveOrder(order1).getBody();
        orderController.saveOrder(order2);
        orderController.saveOrder(order3);
        Order getOrder = orderController.getOrderById(savedOrder.getId()).getBody();
        assertNotNull(getOrder);
        assertEquals(order1.getId(), getOrder.getId());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenOrderIdThenShouldNotReturnRespectiveOrder() throws OrderNotFoundException {
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderController.getOrderById(order1.getId()));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenOrderIdToDeleteThenShouldReturnDeletedOrder() throws OrderAlreadyExistsException, OrderNotFoundException {
        Order savedOrder = orderController.saveOrder(order1).getBody();
        orderController.saveOrder(order2);
        orderController.saveOrder(order3);
        Order deletedOrder = orderController.deleteOrder(savedOrder.getId()).getBody();
        assertNotNull(deletedOrder);
        assertEquals(order1.getId(), deletedOrder.getId());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenOrderIdToDeleteThenShouldNotReturnDeletedOrder() throws OrderNotFoundException {
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderController.deleteOrder(order1.getId()));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenOrderToUpdateThenShouldNotReturnUpdatedOrder() throws OrderAlreadyExistsException, OrderNotFoundException {
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderController.updateOrder(order1));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenOrderToUpdateThenShouldReturnUpdatedOrder() throws OrderAlreadyExistsException, OrderNotFoundException {
        Order savedOrder = orderController.saveOrder(order1).getBody();
        assertNotNull(savedOrder);
        assertEquals(order1.getId(), savedOrder.getId());
        ;
        savedOrder.setUserId(order2.getUserId());
        Order updatedOrder = orderController.updateOrder(savedOrder).getBody();
        assertNotNull(savedOrder);
        assertEquals(savedOrder, updatedOrder);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenGetAllOrdersByUserIdThenShouldReturnListOfAllRespectiveOrders() {
        orderList.add(order1);
        orderList.add(order2);
        orderController.saveOrder(order1);
        orderController.saveOrder(order2);
        orderController.saveOrder(order3);
        List<Order> orders = orderController.getOrderByUserId(order1.getUserId()).getBody();
        assertNotNull(orders);
        assertEquals(orderList, orders);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    /******* VALIDATION *****/
    @Test
    void givenValidOrderThenReturnRespectiveOrder() throws OrderAlreadyExistsException {
        Order savedOrder = orderController.saveOrder(order1).getBody();
        assertEquals(order1.getId(), savedOrder.getId());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenOrderWithInvalidIdToDeleteThenThrowsException() throws OrderAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            order1.setId("");
            Order savedOrder = orderController.saveOrder(order1).getBody();
            orderController.deleteOrder(savedOrder.getId()).getBody();
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenOrderWithInvalidIdToGetThenThrowsException() throws OrderAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            order1.setId("");
            Order savedOrder = orderController.saveOrder(order1).getBody();
            orderController.getOrderById(savedOrder.getId()).getBody();
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenOrderWithInvalidUserIdThenThrowsException() throws OrderAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            order1.setUserId(-1);
            orderController.saveOrder(order1);
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }
}