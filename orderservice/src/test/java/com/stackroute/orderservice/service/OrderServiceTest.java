package com.stackroute.orderservice.service;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order, order1, order2;
    private Product product1, product2, product3;
    private OrderProduct orderProduct1, orderProduct2, orderProduct3;
    private List<OrderProduct> orderProductList;
    private List<Order> orderList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
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
        order = new Order(orderProductList, 1);
        order.setId("1");
        order1 = new Order(orderProductList, 1);
        order1.setId("2");
        order2 = new Order(orderProductList, 2);
        order2.setId("3");
        orderList = new ArrayList<Order>();
    }

    @AfterEach
    public void tearDown() {
        orderRepository.deleteAll();
        order = order1 = order2 = null;
        orderProduct1 = orderProduct2 = orderProduct3 = null;
        product1 = product2 = product3 = null;
        orderProductList = null;
        orderList = null;
    }

    @Test
    public void givenOrderToSaveThenShouldReturnSavedOrder() throws OrderAlreadyExistsException {
        when(orderRepository.insert(order)).thenReturn(order);
        assertEquals(order, orderService.saveOrder(order));
        verify(orderRepository, times(1)).insert(order);
    }

    @Test
    public void givenOrderWithDuplicateIdToSaveThenShouldNotReturnSavedOrder() throws OrderAlreadyExistsException {
        when(orderRepository.existsById(order.getId())).thenReturn(true);
        Assertions.assertThrows(OrderAlreadyExistsException.class, () -> orderService.saveOrder(order));
        verify(orderRepository, times(1)).existsById(anyString());
    }

    @Test
    public void givenGetAllOrdersThenShouldReturnListOfAllOrders() {
        orderList.add(order);
        when(orderRepository.findAll()).thenReturn(orderList);
        assertEquals(orderList, orderService.getAllOrders());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void givenOrderIdThenShouldReturnRespectiveOrder() throws OrderNotFoundException {
        when(orderRepository.findById(anyString())).thenReturn(Optional.of(order));
        assertEquals(order, orderService.getOrderById(order.getId()));
        verify(orderRepository, times(1)).findById(anyString());
    }

    @Test
    void givenOrderIdThenShouldNotReturnRespectiveOrder() throws OrderNotFoundException {
        when(orderRepository.findById(any())).thenThrow(OrderNotFoundException.class);
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(order.getId()));
        verify(orderRepository, times(1)).findById(anyString());
    }

    @Test
    void givenOrderIdToDeleteThenShouldReturnDeletedOrder() throws OrderNotFoundException {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        Order deletedOrder = orderService.deleteOrder(order.getId());
        assertEquals(order.getId(), deletedOrder.getId());
        verify(orderRepository, times(1)).findById(anyString());
        verify(orderRepository, times(1)).deleteById(anyString());
    }

    @Test
    void givenOrderIdToDeleteThenShouldNotReturnDeletedOrder() throws OrderNotFoundException {
        when(orderRepository.findById(order.getId())).thenThrow(OrderNotFoundException.class);
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(order.getId()));
        verify(orderRepository, times(1)).findById(anyString());
    }

    @Test
    public void givenOrderToUpdateThenShouldReturnUpdatedOrder() throws OrderAlreadyExistsException, OrderNotFoundException {
        when(orderRepository.save(any())).thenReturn(order);
        when(orderRepository.existsById(order.getId())).thenReturn(true);
        when(orderRepository.findById(anyString())).thenReturn(Optional.of(order));
        assertEquals(order, orderService.getOrderById(order.getId()));
        order.setUserId(order1.getUserId());
        Order updatedOrder = orderService.updateOrder(order);
        assertEquals(updatedOrder.getUserId(), order1.getUserId());
        verify(orderRepository, times(1)).findById(anyString());
        verify(orderRepository, times(1)).save(any());
        verify(orderRepository, times(1)).existsById(anyString());
    }

    @Test
    public void givenOrderToUpdateThenShouldNotReturnUpdatedOrder() throws OrderNotFoundException {
        when(orderRepository.existsById(order.getId())).thenReturn(false);
        Assertions.assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(order));
        verify(orderRepository, times(1)).existsById(anyString());
    }

    @Test
    public void givenGetAllOrdersByUserIdThenShouldReturnListOfAllRespectiveOrders() {
        orderList.add(order1);
        orderList.add(order2);
        when(orderRepository.findByUserId(anyLong())).thenReturn(orderList);
        assertEquals(orderList, orderService.getOrdersByUserId(order1.getUserId()));
        verify(orderRepository, times(1)).findByUserId(anyLong());
    }
}