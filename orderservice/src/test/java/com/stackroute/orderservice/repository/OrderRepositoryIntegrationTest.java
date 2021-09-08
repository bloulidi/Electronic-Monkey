package com.stackroute.orderservice.repository;

import com.stackroute.orderservice.exception.OrderAlreadyExistsException;
import com.stackroute.orderservice.exception.OrderNotFoundException;
import com.stackroute.orderservice.model.Order;
import com.stackroute.orderservice.model.OrderProduct;
import com.stackroute.orderservice.model.product.Category;
import com.stackroute.orderservice.model.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
public class OrderRepositoryIntegrationTest {

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
        Order savedOrder = orderRepository.save(order1);
        assertNotNull(savedOrder);
        assertEquals(order1.getId(), savedOrder.getId());
    }

    @Test
    public void givenGetAllOrdersThenShouldReturnListOfAllOrders() {
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        List<Order> orders = (List<Order>) orderRepository.findAll();
        assertNotNull(orders);
        assertEquals(orderList, orders);
    }

    @Test
    public void givenOrderIdThenShouldReturnRespectiveOrder() throws OrderNotFoundException {
        Order savedOrder = orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        Order getOrder = orderRepository.findById(savedOrder.getId()).get();
        assertNotNull(getOrder);
        assertEquals(order1.getId(), getOrder.getId());
    }

    @Test
    void givenOrderIdToDeleteThenShouldReturnDeletedOrder() throws OrderNotFoundException {
        Order savedOrder = orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        orderRepository.deleteById(savedOrder.getId());
        orderList.add(order2);
        orderList.add(order3);
        List<Order> orders = (List<Order>) orderRepository.findAll();
        assertNotNull(orders);
        assertEquals(orderList, orders);
    }

    @Test
    public void givenOrderToUpdateThenShouldReturnUpdatedOrder() {
        Order savedOrder = orderRepository.save(order1);
        assertNotNull(savedOrder);
        assertEquals(order1.getId(), savedOrder.getId());
        ;
        savedOrder.setUserId(order2.getUserId());
        assertEquals(true, orderRepository.existsById(savedOrder.getId()));
        Order updatedOrder = orderRepository.save(savedOrder);
        assertNotNull(savedOrder);
        assertEquals(savedOrder, updatedOrder);
    }

    @Test
    public void givenGetAllOrdersByUserIdThenShouldReturnListOfAllRespectiveOrders() {
        orderList.add(order1);
        orderList.add(order2);
        orderRepository.insert(order1);
        orderRepository.insert(order2);
        orderRepository.insert(order3);
        List<Order> orders = orderRepository.findByUserId(order2.getUserId());
        assertNotNull(orders);
        assertEquals(orderList, orders);
    }
}