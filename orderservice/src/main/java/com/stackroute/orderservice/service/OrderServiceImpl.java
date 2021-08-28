package com.stackroute.orderservice.service;
import com.stackroute.orderservice.model.Order;
import com.stackroute.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    @Override
    public Order saveOrder(Order order) {
        return (Order) orderRepository.save(order);    }
}