package com.stackroute.catalog.service;

import com.stackroute.catalog.exception.ProductAlreadyExistsException;
import com.stackroute.catalog.exception.ProductNotFoundException;
import com.stackroute.catalog.model.Category;
import com.stackroute.catalog.model.Photo;
import com.stackroute.catalog.model.Product;
import com.stackroute.catalog.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;
    private Product product, product1, product2;
    private List<Product> productList;
    private Optional optional;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        product = new Product("Dell Laptop", "Good computer", Category.COMPUTERS.getCategory(), 800.5F, new Photo(), 1, false);
        product.setId("1");
        product1 = new Product("Apple iPhone 12", "Good phone", Category.PHONES.getCategory(), 1000.99F, new Photo(), 1, false);
        product1.setId("2");
        product2 = new Product("Charger", "Good charger", Category.ACCESSORIES.getCategory(), 20, new Photo(), 2, true);
        product2.setId("3");
        productList = new ArrayList<Product>();
        optional = Optional.of(product);
    }

    @AfterEach
    public void tearDown() {
        product = product1 = product2 = null;
        productList = null;
        optional = null;
    }

    @Test
    public void givenProductToSaveThenShouldReturnSavedProduct() throws ProductAlreadyExistsException, IOException {
        when(productRepository.insert(product)).thenReturn(product);
        assertEquals(product, productService.saveProduct(product, any()));
        verify(productRepository, times(1)).insert(product);
    }

    @Test
    public void givenProductWithDuplicateIdToSaveThenShouldNotReturnSavedProduct() throws ProductAlreadyExistsException {
        when(productRepository.existsById(product.getId())).thenReturn(true);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(product, any()));
        verify(productRepository, times(1)).existsById(anyString());
    }

    @Test
    public void givenGetAllProductsThenShouldReturnListOfAllProducts() {
        productList.add(product);
        when(productRepository.findAll()).thenReturn(productList);
        assertEquals(productList, productService.getAllProducts());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void givenProductIdThenShouldReturnRespectiveProduct() throws ProductNotFoundException {
        when(productRepository.findById(anyString())).thenReturn(optional);
        assertEquals(product, productService.getProductById(product.getId()));
        verify(productRepository, times(1)).findById(anyString());
    }

    @Test
    void givenProductIdThenShouldNotReturnRespectiveProduct() throws ProductNotFoundException {
        when(productRepository.findById(any())).thenThrow(ProductNotFoundException.class);
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getProductById(product.getId()));
        verify(productRepository, times(1)).findById(anyString());
    }

    @Test
    void givenProductIdToDeleteThenShouldReturnDeletedProduct() throws ProductNotFoundException {
        when(productRepository.findById(product.getId())).thenReturn(optional);
        Product deletedProduct = productService.deleteProduct(product.getId());
        assertEquals(product.getId(), deletedProduct.getId());
        verify(productRepository, times(1)).findById(anyString());
        verify(productRepository, times(1)).deleteById(anyString());
    }

    @Test
    void givenProductIdToDeleteThenShouldNotReturnDeletedProduct() throws ProductNotFoundException {
        when(productRepository.findById(product.getId())).thenThrow(ProductNotFoundException.class);
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(product.getId()));
        verify(productRepository, times(1)).findById(anyString());
    }

    @Test
    public void givenProductToUpdateThenShouldReturnUpdatedProduct() throws ProductAlreadyExistsException, ProductNotFoundException, IOException {
        when(productRepository.save(any())).thenReturn(product);
        when(productRepository.existsById(product.getId())).thenReturn(true);
        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));
        assertEquals(product, productService.getProductById(product.getId()));
        product.setPrice(product1.getPrice());
        Product updatedProduct = productService.updateProduct(product, any());
        assertEquals(updatedProduct.getPrice(), product1.getPrice());
        verify(productRepository, times(1)).findById(anyString());
        verify(productRepository, times(1)).save(any());
        verify(productRepository, times(1)).existsById(anyString());
    }

    @Test
    public void givenProductToUpdateThenShouldNotReturnUpdatedProduct() throws ProductNotFoundException {
        when(productRepository.existsById(product.getId())).thenReturn(false);
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(product, any()));
        verify(productRepository, times(1)).existsById(anyString());
    }

    @Test
    public void givenGetAllProductsByCategoryThenShouldReturnListOfAllRespectiveProducts() {
        productList.add(product1);
        when(productRepository.findByCategory(anyString())).thenReturn(productList);
        assertEquals(productList, productService.getProductsByCategory(product1.getCategory()));
        verify(productRepository, times(1)).findByCategory(anyString());
    }

    @Test
    public void givenGetAllProductsByUserIdThenShouldReturnListOfAllRespectiveProducts() {
        productList.add(product1);
        productList.add(product2);
        when(productRepository.findByUserId(anyLong())).thenReturn(productList);
        assertEquals(productList, productService.getProductsByUserId(product1.getUserId()));
        verify(productRepository, times(1)).findByUserId(anyLong());
    }
}