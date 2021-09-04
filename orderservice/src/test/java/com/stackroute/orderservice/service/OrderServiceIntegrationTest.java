package com.stackroute.orderservice.service;

import com.stackroute.orderservice.exception.OrderAlreadyExistsException;
import com.stackroute.orderservice.exception.OrderNotFoundException;
import com.stackroute.orderservice.model.Order;
import com.stackroute.orderservice.model.OrderProduct;
import com.stackroute.orderservice.model.product.Category;
import com.stackroute.orderservice.model.product.Product;
import com.stackroute.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderRepository orderRepository;

    private Order order1, order2, order3;
    private Product product1, product2, product3;
    private OrderProduct orderProduct1, orderProduct2, orderProduct3;
    private List<OrderProduct> orderProductList;
    private List<Order> orderList;

    @BeforeEach
    void setUp() {
        product1 = new Product("1", "Dell XPS 15", Category.COMPUTERS.getCategory(), 800.5F);
        orderProduct1 = new OrderProduct(2, product1);
        product2 = new Product("2", "Apple iPhone 12", Category.PHONES.getCategory(), 1000.99F);
        orderProduct1 = new OrderProduct(3, product2);
        product3 = new Product("3", "Apple charger", Category.ACCESSORIES.getCategory(), 20);
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
    public void givenOrderToSaveThenShouldReturnSavedOrder() throws OrderAlreadyExistsException {
        Order savedOrder = orderService.saveOrder(order1);
        assertNotNull(savedOrder);
        assertEquals(order1.getId(), savedOrder.getId());
    }

    @Test
    public void givenOrderToSaveThenShouldNotReturnSavedOrder() throws OrderAlreadyExistsException {
        orderService.saveOrder(order1);
        Assertions.assertThrows(OrderAlreadyExistsException.class, () -> orderService.saveOrder(order1));
    }

    @Test
    public void givenOrderWithDuplicateIdToSaveThenShouldNotReturnSavedOrder() throws OrderAlreadyExistsException {
        Order savedOrder = orderService.saveOrder(order1);
        assertNotNull(savedOrder);
        order2.setId(order1.getId());
        Assertions.assertThrows(OrderAlreadyExistsException.class, () -> orderService.saveOrder(order2));
    }

    @Test
    public void givenGetAllOrdersThenShouldReturnListOfAllOrders() throws OrderAlreadyExistsException {
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        orderService.saveOrder(order1);
        orderService.saveOrder(order2);
        orderService.saveOrder(order3);
        List<Order> orders = orderService.getAllOrders();
        assertNotNull(orders);
        assertEquals(orderList, orders);
    }

    @Test
    public void givenOrderIdThenShouldReturnRespectiveOrder() throws OrderAlreadyExistsException, OrderNotFoundException {
        Order savedOrder = orderService.saveOrder(order1);
        orderService.saveOrder(order2);
        orderService.saveOrder(order3);
        Order getOrder = orderService.getOrderById(savedOrder.getId());
        assertNotNull(getOrder);
        assertEquals(order1.getId(), getOrder.getId());
    }

    @Test
    void givenOrderIdThenShouldNotReturnRespectiveOrder() throws OrderNotFoundException {
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(order1.getId()));
    }

    @Test
    void givenOrderIdToDeleteThenShouldReturnDeletedOrder() throws OrderAlreadyExistsException, OrderNotFoundException {
        Order savedOrder = orderService.saveOrder(order1);
        orderService.saveOrder(order2);
        orderService.saveOrder(order3);
        Order deletedOrder = orderService.deleteOrder(savedOrder.getId());
        assertNotNull(deletedOrder);
        assertEquals(order1.getId(), deletedOrder.getId());
    }

    @Test
    void givenOrderIdToDeleteThenShouldNotReturnDeletedOrder() throws OrderNotFoundException {
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(order1.getId()));
    }

    @Test
    public void givenOrderToUpdateThenShouldReturnUpdatedOrder() throws OrderAlreadyExistsException, OrderNotFoundException {
        Order savedOrder = orderService.saveOrder(order1);
        assertNotNull(savedOrder);
        assertEquals(order1.getId(), savedOrder.getId());
        ;
        savedOrder.setUserId(order2.getUserId());
        Order updatedOrder = orderService.updateOrder(savedOrder);
        assertNotNull(savedOrder);
        assertEquals(savedOrder, updatedOrder);
    }

    @Test
    public void givenOrderToUpdateThenShouldNotReturnUpdatedOrder() throws OrderNotFoundException {
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(order1));
    }

    @Test
    public void givenGetAllOrdersByUserIdThenShouldReturnListOfAllRespectiveOrders() {
        orderList.add(order1);
        orderList.add(order2);
        orderService.saveOrder(order1);
        orderService.saveOrder(order2);
        orderService.saveOrder(order3);
        List<Order> orders = orderService.getOrdersByUserId(order1.getUserId());
        assertNotNull(orders);
        assertEquals(orderList, orders);
    }

    /******* VALIDATION *****/

    @Test
    void givenValidOrderThenReturnRespectiveOrder() throws OrderAlreadyExistsException {
        Order savedOrder = orderService.saveOrder(order1);
        assertEquals(order1.getId(), savedOrder.getId());
    }

    @Test
    void givenOrderWithInvalidIdToDeleteThenThrowsException() throws OrderAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            order1.setId("");
            Order savedOrder = orderService.saveOrder(order1);
            orderService.deleteOrder(savedOrder.getId());
        });
    }

    @Test
    void givenOrderWithInvalidIdToGetThenThrowsException() throws OrderAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            order1.setId("");
            Order savedOrder = orderService.saveOrder(order1);
            orderService.getOrderById(savedOrder.getId());
        });
    }

    @Test
    void givenOrderWithInvalidStatusThenThrowsException() throws OrderAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            order1.setStatus("");
            orderService.saveOrder(order1);
        });
    }

    @Test
    void givenOrderWithInvalidUserIdThenThrowsException() throws OrderAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            order1.setUserId(-1);
            orderService.saveOrder(order1);
        });
    }
}