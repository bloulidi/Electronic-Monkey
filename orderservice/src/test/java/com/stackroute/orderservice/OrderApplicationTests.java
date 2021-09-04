package com.stackroute.orderservice;

import com.stackroute.orderservice.controller.OrderController;
import com.stackroute.orderservice.repository.OrderRepository;
import com.stackroute.orderservice.service.OrderServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class OrderApplicationTests {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderController orderController;

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertThat(orderService).isNotNull();
        assertThat(orderController).isNotNull();
        assertThat(orderRepository).isNotNull();
    }
}
