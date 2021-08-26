package com.stackroute.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.catalog.exception.GlobalExceptionHandler;
import com.stackroute.catalog.exception.ProductAlreadyExistsException;
import com.stackroute.catalog.exception.ProductNotFoundException;
import com.stackroute.catalog.model.Category;
import com.stackroute.catalog.model.Product;
import com.stackroute.catalog.service.ProductService;
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
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product, product1, product2;
    private List<Product> productList;

    @BeforeEach
    void setUp() {
        initMocks(this);
        mockMvc = standaloneSetup(productController).setControllerAdvice(new GlobalExceptionHandler()).build();
        product = new Product("1AB", "Dell Laptop", "Good computer", Category.COMPUTERS.getCategory(), 800.5F);
        product.setId("1");
        product1 = new Product("2AB", "Apple iPhone 12", "Good phone", Category.PHONES.getCategory(), 1000.99F);
        product2 = new Product("3AB", "Charger", "Good charger", Category.ACCESSORIES.getCategory(), 20);
        productList = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        product = product1 = product2 = null;
        productList = null;
    }

    @Test
    void givenProductToSaveThenShouldReturnSavedProduct() throws Exception {
        when(productService.saveProduct(any())).thenReturn(product);
        mockMvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(productService).saveProduct(any());
        verify(productService, times(1)).saveProduct(any());
    }

    @Test
    void givenProductToSaveThenShouldNotReturnSavedProduct() throws Exception {
        when(productService.saveProduct((Product) any())).thenThrow(ProductAlreadyExistsException.class);
        mockMvc.perform(post("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(productService).saveProduct(any());
        verify(productService, times(1)).saveProduct(any());
    }

    @Test
    void givenGetAllProductsThenShouldReturnListOfAllProducts() throws Exception {
        productList.add(product);
        productList.add(product1);
        productList.add(product2);
        when(productService.getAllProducts()).thenReturn(productList);
        mockMvc.perform(get("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(productService).getAllProducts();
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void givenProductIdThenShouldReturnRespectiveProduct() throws Exception, ProductNotFoundException {
        when(productService.getProductById(product.getId())).thenReturn(product);
        mockMvc.perform(get("/api/v1/products/" + product.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(productService).getProductById(anyString());
        verify(productService, times(1)).getProductById(anyString());
    }

    @Test
    void givenProductIdToDeleteThenShouldReturnDeletedProduct() throws Exception {
        when(productService.deleteProduct(product.getId())).thenReturn(product);
        mockMvc.perform(delete("/api/v1/products/" + product.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(productService).deleteProduct(anyString());
        verify(productService, times(1)).deleteProduct(anyString());
    }

    @Test
    void givenProductIdToDeleteThenShouldNotReturnDeletedProduct() throws ProductNotFoundException, Exception {
        when(productService.deleteProduct(product.getId())).thenThrow(ProductNotFoundException.class);
        mockMvc.perform(delete("/api/v1/products/" + product.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(productService).deleteProduct(anyString());
        verify(productService, times(1)).deleteProduct(anyString());
    }

    @Test
    public void givenProductToUpdateThenShouldReturnUpdatedProduct() throws Exception {
        when(productService.updateProduct(any())).thenReturn(product);
        mockMvc.perform(patch("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(productService).updateProduct(any());
        verify(productService, times(1)).updateProduct(any());
    }

    @Test
    public void givenProductToUpdateThenShouldNotReturnUpdatedProduct() throws ProductNotFoundException, Exception {
        when(productService.updateProduct(any())).thenThrow(ProductNotFoundException.class);
        mockMvc.perform(patch("/api/v1/products").contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(productService).updateProduct(any());
        verify(productService, times(1)).updateProduct(any());
    }

    @Test
    public void givenProductByCodeThenShouldReturnRespectiveProduct() throws Exception {
        productList.add(product);
        productList.add(product2);
        when(productService.getProductByCode(product.getCode())).thenReturn(product);
        mockMvc.perform(get("/api/v1/products/code/" + product.getCode()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(productService).getProductByCode(anyString());
        verify(productService, times(1)).getProductByCode(anyString());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
