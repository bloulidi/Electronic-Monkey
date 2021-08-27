package com.stackroute.catalog.controller;

import com.stackroute.catalog.exception.ProductAlreadyExistsException;
import com.stackroute.catalog.exception.ProductNotFoundException;
import com.stackroute.catalog.model.Category;
import com.stackroute.catalog.model.Product;
import com.stackroute.catalog.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductControllerIntegrationTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductRepository productRepository;

    private Product product1, product2, product3;
    private List<Product> productList;
    //private List<String> productsCode, savedProductsCode;
    private Logger logger;

    @BeforeEach
    void setUp() {
        logger = LoggerFactory.getLogger(ProductController.class);
        product1 = new Product("Dell Laptop", "Good computer", Category.COMPUTERS.getCategory(), 800.5F);
        product1.setId("1");
        product2 = new Product("Apple iPhone 12", "Good phone", Category.PHONES.getCategory(), 1000.99F);
        product2.setId("2");
        product3 = new Product("Charger", "Good charger", Category.ACCESSORIES.getCategory(), 20);
        product3.setId("3");
        productList = new ArrayList<Product>();
        //savedProductsCode = new ArrayList<>();
        //productsCode = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
        product1 = product2 = product3 = null;
        productList = null;
        //productsCode = savedProductsCode = null;
    }

    @Test
    public void givenProductToSaveThenShouldReturnSavedProduct() throws ProductAlreadyExistsException {
        Product savedProduct = productController.saveProduct(product1).getBody();
        assertNotNull(savedProduct);
        assertEquals(product1.getId(), savedProduct.getId());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenProductToSaveThenShouldNotReturnSavedProduct() throws ProductAlreadyExistsException {
        productController.saveProduct(product1);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productController.saveProduct(product1));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenGetAllProductsThenShouldReturnListOfAllProducts() throws ProductAlreadyExistsException {
        Product savedProduct1 = productController.saveProduct(product1).getBody();
        Product savedProduct2 = productController.saveProduct(product2).getBody();
        Product savedProduct3 = productController.saveProduct(product3).getBody();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        List<Product> products = productController.getAllProducts().getBody();
        /*for (Product product : productList) {
            productsCode.add(product.getCode());
        }
        for (Product product : products) {
            savedProductsCode.add(product.getCode());
        }*/
        assertNotNull(products);
        assertEquals(productList, products);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenProductIdThenShouldReturnRespectiveProduct() throws ProductAlreadyExistsException, ProductNotFoundException {
        Product savedProduct = productController.saveProduct(product1).getBody();
        productController.saveProduct(product2);
        productController.saveProduct(product3);
        Product getProduct = productController.getProductById(savedProduct.getId()).getBody();
        assertNotNull(getProduct);
        assertEquals(product1.getId(), getProduct.getId());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductIdThenShouldNotReturnRespectiveProduct() throws ProductNotFoundException {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productController.getProductById(product1.getId()));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    /*@Test
    public void givenProductCodeThenShouldReturnRespectiveProduct() throws ProductAlreadyExistsException, ProductNotFoundException {
        Product savedProduct = productController.saveProduct(product1).getBody();
        productController.saveProduct(product2);
        productController.saveProduct(product3);
        Product getProduct = productController.getProductByCode(savedProduct.getId()).getBody();
        assertNotNull(getProduct);
        assertEquals(product1.getCode(), getProduct.getCode());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductCodeThenShouldNotReturnRespectiveProduct() throws ProductNotFoundException {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productController.getProductByCode(product1.getCode()));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }*/

    @Test
    void givenProductIdToDeleteThenShouldReturnDeletedProduct() throws ProductAlreadyExistsException, ProductNotFoundException {
        Product savedProduct = productController.saveProduct(product1).getBody();
        productController.saveProduct(product2);
        productController.saveProduct(product3);
        Product deletedProduct = productController.deleteProduct(savedProduct.getId()).getBody();
        assertNotNull(deletedProduct);
        assertEquals(product1.getId(), deletedProduct.getId());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductIdToDeleteThenShouldNotReturnDeletedProduct() throws ProductNotFoundException {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productController.deleteProduct(product1.getId()));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenProductToUpdateThenShouldReturnUpdatedProduct() throws ProductAlreadyExistsException, ProductNotFoundException{
        Product savedProduct = productController.saveProduct(product1).getBody();
        assertNotNull(savedProduct);
        assertEquals(product1.getId(), savedProduct.getId());
        savedProduct.setPrice(product2.getPrice());
        Product updatedProduct = productController.updateProduct(savedProduct).getBody();
        assertNotNull(savedProduct);
        assertEquals(savedProduct, updatedProduct);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    /*@Test
    public void givenProductToUpdateWithAlreadyExistingCodeThenShouldNotReturnUpdatedProduct() throws ProductNotFoundException, ProductAlreadyExistsException {
        Product savedProduct = productController.saveProduct(product1).getBody();
        productController.saveProduct(product2);
        assertNotNull(savedProduct);
        assertEquals(product1.getCode(), savedProduct.getCode());;
        savedProduct.setCode(product2.getCode());
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productController.updateProduct(savedProduct).getBody());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }*/

    @Test
    public void givenProductToUpdateThenShouldNotReturnUpdatedProduct() throws ProductAlreadyExistsException, ProductNotFoundException {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productController.updateProduct(product1));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    /******* VALIDATION *****/
    @Test
    void givenValidProductThenReturnRespectiveProduct() throws ProductAlreadyExistsException {
        Product savedProduct = productController.saveProduct(product1).getBody();
        assertEquals(product1.getId(), savedProduct.getId());
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductWithInvalidIdToDeleteThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException{
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setId("");
            Product savedProduct = productController.saveProduct(product1).getBody();
            productController.deleteProduct(savedProduct.getId()).getBody();
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductWithInvalidIdToGetThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException{
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setId("");
            Product savedProduct = productController.saveProduct(product1).getBody();
            productController.getProductById(savedProduct.getId()).getBody();
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    /*@Test
    void givenProductWithInvalidCodeThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setCode("");
            productController.saveProduct(product1);
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }*/

    @Test
    void givenProductWithInvalidCategoryThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException{
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setCategory("sdfsdfs");
            productController.saveProduct(product1);
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductWithInvalidTitleThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setTitle("");
            productController.saveProduct(product1);
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }
}