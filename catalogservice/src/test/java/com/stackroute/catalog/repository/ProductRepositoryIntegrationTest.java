package com.stackroute.catalog.repository;

import com.stackroute.catalog.exception.ProductAlreadyExistsException;
import com.stackroute.catalog.exception.ProductNotFoundException;
import com.stackroute.catalog.model.Category;
import com.stackroute.catalog.model.Product;
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
public class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product1, product2, product3;
    private List<Product> productList;
    private List<String> productsCode, savedProductsCode;

    @BeforeEach
    void setUp() {
        product1 = new Product("1AB", "Dell Laptop", "Good computer", Category.COMPUTERS.getCategory(), 800.5F);
        product1.setId("1");
        product2 = new Product("2AB", "Apple iPhone 12", "Good phone", Category.PHONES.getCategory(), 1000.99F);
        product3 = new Product("3AB", "Charger", "Good charger", Category.ACCESSORIES.getCategory(), 20);
        productList = new ArrayList<Product>();
        savedProductsCode = new ArrayList<>();
        productsCode = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
        product1 = product2 = product3 = null;
        productList = null;
        productsCode = savedProductsCode = null;
    }

    @Test
    public void givenProductToSaveThenShouldReturnSavedProduct() throws ProductAlreadyExistsException {
        Product savedProduct = productRepository.save(product1);
        assertNotNull(savedProduct);
        assertEquals(product1.getCode(), savedProduct.getCode());
    }

    @Test
    public void givenGetAllProductsThenShouldReturnListOfAllProducts() {
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        List<Product> products = (List<Product>) productRepository.findAll();
        for (Product product : productList) { productsCode.add(product.getCode()); }
        for (Product product : products) { savedProductsCode.add(product.getCode()); }
        assertNotNull(products);
        assertEquals(productsCode, savedProductsCode);
    }

    @Test
    public void givenProductIdThenShouldReturnRespectiveProduct() throws ProductNotFoundException {
        Product savedProduct = productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        Product getProduct = productRepository.findById(savedProduct.getId()).get();
        assertNotNull(getProduct);
        assertEquals(product1.getCode(), getProduct.getCode());
    }

    @Test
    public void givenProductCodeThenShouldReturnRespectiveProduct() throws ProductNotFoundException {
        Product savedProduct = productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        Product getProduct = productRepository.findByCode(savedProduct.getCode());
        assertNotNull(getProduct);
        assertEquals(product1.getCode(), getProduct.getCode());
    }

    @Test
    void givenProductIdToDeleteThenShouldReturnDeletedProduct() throws ProductNotFoundException {
        Product savedProduct = productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.deleteById(savedProduct.getId());
        productList.add(product2);
        productList.add(product3);
        List<Product> products = (List<Product>)productRepository.findAll();
        for (Product product : productList) { productsCode.add(product.getCode()); }
        for (Product product : products) { savedProductsCode.add(product.getCode()); }
        assertNotNull(products);
        assertEquals(productsCode, savedProductsCode);
    }

    @Test
    public void givenProductToUpdateThenShouldReturnUpdatedProduct() {
        Product savedProduct = productRepository.save(product1);
        assertNotNull(savedProduct);
        assertEquals(product1.getCode(), savedProduct.getCode());;
        savedProduct.setPrice(product2.getPrice());
        assertEquals(true, productRepository.existsById(savedProduct.getId()));
        Product updatedProduct = productRepository.save(savedProduct);
        assertNotNull(savedProduct);
        assertEquals(savedProduct, updatedProduct);
    }
}