package com.stackroute.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.orderservice.exception.GlobalExceptionHandler;
import com.stackroute.orderservice.exception.OrderAlreadyExistsException;
import com.stackroute.orderservice.exception.OrderNotFoundException;
import com.stackroute.orderservice.model.Order;
import com.stackroute.orderservice.model.OrderProduct;
import com.stackroute.orderservice.model.product.Category;
import com.stackroute.orderservice.model.product.Product;
import com.stackroute.orderservice.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    OrderService orderService;
    private MockMvc mockMvc;
    @InjectMocks
    private OrderController orderController;

    private Order order, order1, order2;
    private Product product1, product2, product3;
    private OrderProduct orderProduct1, orderProduct2, orderProduct3;
    private List<OrderProduct> orderProductList;
    private List<Order> orderList;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        initMocks(this);
        mockMvc = standaloneSetup(orderController).setControllerAdvice(new GlobalExceptionHandler()).build();
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
        product1 = product2 = product3 = null;
        orderProduct1 = orderProduct2 = orderProduct3 = null;
        order = order1 = order2 = null;
        orderProductList = null;
        orderList = null;
    }

    @Test
    void givenOrderToSaveThenShouldReturnSavedOrder() throws OrderAlreadyExistsException, Exception {
        when(orderService.saveOrder(any())).thenReturn(order);
        mockMvc.perform(post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON).content(asJsonString(order)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(orderService).saveOrder(any());
        verify(orderService, times(1)).saveOrder(any());
    }

    @Test
    void givenOrderToSaveThenShouldNotReturnSavedOrder() throws OrderAlreadyExistsException, Exception {
        when(orderService.saveOrder((Order) any())).thenThrow(OrderAlreadyExistsException.class);
        mockMvc.perform(post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON).content(asJsonString(order)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(orderService).saveOrder(any());
        verify(orderService, times(1)).saveOrder(any());
    }

    @Test
    void givenGetAllOrdersThenShouldReturnListOfAllOrders() throws Exception {
        orderList.add(order);
        orderList.add(order1);
        orderList.add(order2);
        when(orderService.getAllOrders()).thenReturn(orderList);
        mockMvc.perform(get("/api/v1/orders").contentType(MediaType.APPLICATION_JSON).content(asJsonString(order)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(orderService).getAllOrders();
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    void givenOrderIdThenShouldReturnRespectiveOrder() throws Exception, OrderNotFoundException {
        when(orderService.getOrderById(order.getId())).thenReturn(order);
        mockMvc.perform(get("/api/v1/orders/" + order.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(order)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(orderService).getOrderById(anyString());
        verify(orderService, times(1)).getOrderById(anyString());
    }

    @Test
    void givenOrderIdToDeleteThenShouldReturnDeletedOrder() throws OrderNotFoundException, Exception {
        when(orderService.deleteOrder(order.getId())).thenReturn(order);
        mockMvc.perform(delete("/api/v1/orders/" + order.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(order)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(orderService).deleteOrder(anyString());
        verify(orderService, times(1)).deleteOrder(anyString());
    }

    @Test
    void givenOrderIdToDeleteThenShouldNotReturnDeletedOrder() throws OrderNotFoundException, Exception {
        when(orderService.deleteOrder(order.getId())).thenThrow(OrderNotFoundException.class);
        mockMvc.perform(delete("/api/v1/orders/" + order.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(order)))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(orderService).deleteOrder(anyString());
        verify(orderService, times(1)).deleteOrder(anyString());
    }

    @Test
    public void givenOrderToUpdateThenShouldReturnUpdatedOrder() throws OrderNotFoundException, OrderAlreadyExistsException, Exception {
        when(orderService.updateOrder(any())).thenReturn(order);
        mockMvc.perform(patch("/api/v1/orders").contentType(MediaType.APPLICATION_JSON).content(asJsonString(order)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(orderService).updateOrder(any());
        verify(orderService, times(1)).updateOrder(any());
    }

    @Test
    public void givenOrderToUpdateThenShouldNotReturnUpdatedOrder() throws OrderNotFoundException, OrderAlreadyExistsException, Exception {
        when(orderService.updateOrder(any())).thenThrow(OrderNotFoundException.class);
        mockMvc.perform(patch("/api/v1/orders").contentType(MediaType.APPLICATION_JSON).content(asJsonString(order)))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(orderService).updateOrder(any());
        verify(orderService, times(1)).updateOrder(any());
    }

    @Test
    public void givenGetAllOrdersByUserIdThenShouldReturnListOfAllRespectiveOrders() throws Exception {
        orderList.add(order);
        orderList.add(order1);
        when(orderService.getOrdersByUserId(order.getUserId())).thenReturn(orderList);
        mockMvc.perform(get("/api/v1/orders/user/" + order.getUserId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(order)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(orderService).getOrdersByUserId(anyLong());
        verify(orderService, times(1)).getOrdersByUserId(anyLong());
    }
}
