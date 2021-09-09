package com.stackroute.catalog.controller;

import com.stackroute.catalog.exception.GlobalExceptionHandler;
import com.stackroute.catalog.exception.ProductAlreadyExistsException;
import com.stackroute.catalog.exception.ProductNotFoundException;
import com.stackroute.catalog.model.Category;
import com.stackroute.catalog.model.Photo;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static com.stackroute.catalog.controller.ProductControllerIntegrationTest.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    ProductService productService;
    private MockMvc mockMvc;
    @InjectMocks
    private ProductController productController;

    private Product product, product1, product2;
    private List<Product> productList;
    private MockMultipartFile file, file1;

    @BeforeEach
    void setUp() {
        initMocks(this);
        mockMvc = standaloneSetup(productController).setControllerAdvice(new GlobalExceptionHandler()).build();
        product = new Product("Dell Laptop", "Good computer", Category.COMPUTERS.getCategory(), 800.5F, new Photo(), 1, false);
        product.setId("1");
        product1 = new Product("Apple iPhone 12", "Good phone", Category.PHONES.getCategory(), 1000.99F, new Photo(), 1, false);
        product1.setId("2");
        product2 = new Product("Charger", "Good charger", Category.ACCESSORIES.getCategory(), 20, new Photo(), 2, true);
        product2.setId("3");
        file = new MockMultipartFile("image", "file.png", "0", "Some Binary stuff".getBytes());
        file1 = new MockMultipartFile("product", "", "application/json", asJsonString(product).getBytes());
        productList = new ArrayList<Product>();
    }

    @AfterEach
    void tearDown() {
        product = product1 = product2 = null;
        productList = null;
    }

    @Test
    void givenProductToSaveThenShouldReturnSavedProduct() throws ProductAlreadyExistsException, Exception {
        when(productService.saveProduct(any(), any())).thenReturn(product);
        mockMvc.perform(multipart("/api/v1/products").file(file1).file(file))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(productService).saveProduct(any(), any());
        verify(productService, times(1)).saveProduct(any(), any());
    }

    @Test
    void givenProductToSaveThenShouldNotReturnSavedProduct() throws ProductAlreadyExistsException, Exception {
        when(productService.saveProduct(any(), any())).thenThrow(ProductAlreadyExistsException.class);
        mockMvc.perform(multipart("/api/v1/products").file(file1).file(file))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(productService).saveProduct(any(), any());
        verify(productService, times(1)).saveProduct(any(), any());
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
    void givenProductIdToDeleteThenShouldReturnDeletedProduct() throws ProductNotFoundException, Exception {
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

    /*
        @Test
        public void givenProductToUpdateThenShouldReturnUpdatedProduct() throws ProductNotFoundException, ProductAlreadyExistsException, Exception {
            when(productService.updateProduct(any(), any())).thenReturn(product);
            mockMvc.perform(multipart("/api/v1/products").file(file1).file(file))
                    .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
            verify(productService).updateProduct(any(), any());
            verify(productService, times(1)).updateProduct(any(), any());
        }

        @Test
        public void givenProductToUpdateThenShouldNotReturnUpdatedProduct() throws ProductNotFoundException, ProductAlreadyExistsException, Exception {
            when(productService.updateProduct(any(), any())).thenThrow(ProductNotFoundException.class);
            mockMvc.perform(multipart("/api/v1/products").file(file1).file(file))
                    .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
            verify(productService).updateProduct(any(), any());
            verify(productService, times(1)).updateProduct(any(), any());
        }
    */
    @Test
    public void givenGetAllProductsByCategoryThenShouldReturnListOfAllRespectiveProducts() throws Exception {
        productList.add(product);
        when(productService.getProductsByCategory(product.getCategory())).thenReturn(productList);
        mockMvc.perform(get("/api/v1/products/category/" + product.getCategory()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(productService).getProductsByCategory(anyString());
        verify(productService, times(1)).getProductsByCategory(anyString());
    }

    @Test
    public void givenGetAllProductsByCategoryAdminThenShouldReturnListOfAllRespectiveProducts() throws Exception {
        product.setHidden(true);
        productList.add(product);
        when(productService.getProductsByCategory(product.getCategory())).thenReturn(productList);
        mockMvc.perform(get("/api/v1/products/category/admin/" + product.getCategory()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(productService).getProductsByCategory(anyString());
        verify(productService, times(1)).getProductsByCategory(anyString());
    }

    @Test
    public void givenGetAllProductsByUserIdThenShouldReturnListOfAllRespectiveProducts() throws Exception {
        productList.add(product);
        productList.add(product1);
        when(productService.getProductsByUserId(product.getUserId())).thenReturn(productList);
        mockMvc.perform(get("/api/v1/products/user/" + product.getUserId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(productService).getProductsByUserId(anyLong());
        verify(productService, times(1)).getProductsByUserId(anyLong());
    }
}
