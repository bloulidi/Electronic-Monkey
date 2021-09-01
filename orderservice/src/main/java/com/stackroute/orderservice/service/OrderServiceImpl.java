package com.stackroute.orderservice.service;

import com.stackroute.orderservice.exception.OrderAlreadyExistsException;
import com.stackroute.orderservice.exception.OrderNotFoundException;
import com.stackroute.orderservice.model.Order;
import com.stackroute.orderservice.model.Status;
import com.stackroute.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order saveOrder(Order order) throws OrderAlreadyExistsException {
        if(order.getId() != null && !order.getId().isBlank()){
            if(orderRepository.existsById(order.getId())){
                throw new OrderAlreadyExistsException();
            }
        }
        return (Order) orderRepository.insert(order);
    }

    @Override
    public Order getOrderById(String id) throws OrderNotFoundException {
        Order order = null;
        Optional<Order> userOptional = orderRepository.findById(id);
        if(userOptional.isPresent()){
            order = userOptional.get();
        } else {
            throw new OrderNotFoundException();
        }
        return order;
    }

    @Override
    public Order deleteOrder(String id) throws OrderNotFoundException {
        Order order = getOrderById(id);
        if(order != null){
            orderRepository.deleteById(id);
        } else {
            throw new OrderNotFoundException();
        }
        return order;
    }

    @Override
    public Order updateOrder(Order order) throws OrderNotFoundException, OrderAlreadyExistsException {
        if(!orderRepository.existsById(order.getId())){
            throw new OrderNotFoundException();
        }
        return (Order) orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUserId(long userId)  {
        return (List<Order>) orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }
}
