package com.stackroute.catalog.repository;

import com.stackroute.catalog.exception.ProductAlreadyExistsException;
import com.stackroute.catalog.exception.ProductNotFoundException;
import com.stackroute.catalog.model.Category;
import com.stackroute.catalog.model.Photo;
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

    @BeforeEach
    void setUp() {
        product1 = new Product("Dell Laptop", "Good computer", Category.COMPUTERS.getCategory(), 800.5F, new Photo(), 1, false);
        product1.setId("1");
        product2 = new Product("Apple iPhone 12", "Good phone", Category.PHONES.getCategory(), 1000.99F, new Photo(), 1, false);
        product2.setId("2");
        product3 = new Product("Charger", "Good charger", Category.ACCESSORIES.getCategory(), 20, new Photo(), 2, true);
        product3.setId("3");
        productList = new ArrayList<Product>();
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
        product1 = product2 = product3 = null;
        productList = null;
    }

    @Test
    public void givenProductToSaveThenShouldReturnSavedProduct() throws ProductAlreadyExistsException {
        Product savedProduct = productRepository.save(product1);
        assertNotNull(savedProduct);
        assertEquals(product1.getId(), savedProduct.getId());
    }

    @Test
    public void givenGetAllProductsThenShouldReturnListOfAllProducts() {
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productRepository.insert(product1);
        productRepository.insert(product2);
        productRepository.insert(product3);
        List<Product> products = (List<Product>) productRepository.findAll();
        assertNotNull(products);
        assertEquals(productList, products);
    }

    @Test
    public void givenProductIdThenShouldReturnRespectiveProduct() throws ProductNotFoundException {
        Product savedProduct = productRepository.insert(product1);
        productRepository.insert(product2);
        productRepository.insert(product3);
        Product getProduct = productRepository.findById(savedProduct.getId()).get();
        assertNotNull(getProduct);
        assertEquals(product1.getId(), getProduct.getId());
    }

    @Test
    void givenProductIdToDeleteThenShouldReturnDeletedProduct() throws ProductNotFoundException {
        Product savedProduct = productRepository.insert(product1);
        productRepository.insert(product2);
        productRepository.insert(product3);
        productRepository.deleteById(savedProduct.getId());
        productList.add(product2);
        productList.add(product3);
        List<Product> products = (List<Product>) productRepository.findAll();
        assertNotNull(products);
        assertEquals(productList, products);
    }

    @Test
    public void givenProductToUpdateThenShouldReturnUpdatedProduct() {
        Product savedProduct = productRepository.insert(product1);
        assertNotNull(savedProduct);
        assertEquals(product1.getId(), savedProduct.getId());
        savedProduct.setPrice(product2.getPrice());
        assertEquals(true, productRepository.existsById(savedProduct.getId()));
        Product updatedProduct = productRepository.save(savedProduct);
        assertNotNull(savedProduct);
        assertEquals(savedProduct, updatedProduct);
    }

    @Test
    public void givenGetAllProductsByCategoryThenShouldReturnListOfAllRespectiveProducts() {
        productList.add(product2);
        productRepository.insert(product1);
        productRepository.insert(product2);
        productRepository.insert(product3);
        List<Product> products = productRepository.findByCategory(product2.getCategory());
        assertNotNull(products);
        assertEquals(productList, products);
    }

    @Test
    public void givenGetAllProductsByUserIdThenShouldReturnListOfAllRespectiveProducts() {
        productList.add(product1);
        productList.add(product2);
        productRepository.insert(product1);
        productRepository.insert(product2);
        productRepository.insert(product3);
        List<Product> products = productRepository.findByUserId(product2.getUserId());
        assertNotNull(products);
        assertEquals(productList, products);
    }
}