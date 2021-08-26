package com.stackroute.catalog.service;

import com.stackroute.catalog.exception.ProductAlreadyExistsException;
import com.stackroute.catalog.exception.ProductNotFoundException;
import com.stackroute.catalog.model.Category;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        productList = new ArrayList<Product>();
        product = new Product("1AB", "Dell Laptop", "Good computer", Category.COMPUTERS.getCategory(), 800.5F);
        product.setId("1");
        product1 = new Product("2AB", "Apple iPhone 12", "Good phone", Category.PHONES.getCategory(), 1000.99F);
        product2 = new Product("3AB", "Charger", "Good charger", Category.ACCESSORIES.getCategory(), 20);
        optional = Optional.of(product);
    }

    @AfterEach
    public void tearDown() {
        product = null;
    }

    @Test
    public void givenProductToSaveThenShouldReturnSavedProduct() throws ProductAlreadyExistsException {
        when(productRepository.save(any())).thenReturn(product);
        assertEquals(product, productService.saveProduct(product));
        verify(productRepository, times(1)).save(any());
    }

    @Test
    public void givenProductWithDuplicateIdToSaveThenShouldNotReturnSavedProduct() throws ProductAlreadyExistsException {
        when(productRepository.existsById(product.getId())).thenReturn(true);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(product));
        verify(productRepository, times(1)).existsById(anyString());
    }

    @Test
    public void givenProductWithDuplicateCodeToSaveThenShouldNotReturnSavedProduct() throws ProductAlreadyExistsException {
        when(productRepository.existsById(product.getId())).thenReturn(false);
        when(productRepository.existsByCode(product.getCode())).thenReturn(true);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(product));
        verify(productRepository, times(1)).existsById(anyString());
        verify(productRepository, times(1)).existsByCode(anyString());
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
    public void givenProductCodeThenShouldReturnRespectiveProduct() throws ProductNotFoundException {
        when(productRepository.findByCode(any())).thenReturn(product);
        assertEquals(product, productService.getProductByCode(product.getCode()));
        verify(productRepository, times(1)).findByCode(anyString());
    }

    @Test
    void givenProductCodeThenShouldNotReturnRespectiveProduct() throws ProductNotFoundException {
        when(productRepository.findByCode(any())).thenThrow(ProductNotFoundException.class);
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getProductByCode(product.getCode()));
        verify(productRepository, times(1)).findByCode(anyString());
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
    public void givenProductToUpdateThenShouldReturnUpdatedProduct() {
        when(productRepository.save(any())).thenReturn(product);
        when(productRepository.existsById(product.getId())).thenReturn(true);
        product.setPrice(product1.getPrice());
        Product updatedProduct = productService.updateProduct(product);
        assertEquals(updatedProduct.getPrice(), product1.getPrice());
        verify(productRepository, times(1)).save(any());
        verify(productRepository, times(1)).existsById(anyString());
    }

    @Test
    public void givenProductToUpdateThenShouldNotReturnUpdatedProduct() throws ProductNotFoundException {
        when(productRepository.existsById(product.getId())).thenReturn(false);
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(product));
        verify(productRepository, times(1)).existsById(anyString());
    }
}