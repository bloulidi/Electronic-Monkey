package com.stackroute.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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
    private Logger logger;
    private MultipartFile file;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        logger = LoggerFactory.getLogger(ProductController.class);
        file = new MockMultipartFile("File", "file.png", "0", "Some Binary stuff".getBytes());
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
    public void givenProductToSaveThenShouldReturnSavedProduct() throws ProductAlreadyExistsException, IOException {
        Product savedProduct = productController.saveProduct(asJsonString(product1), null).getBody();
        assertNotNull(savedProduct);
        assertEquals(product1, savedProduct);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenProductToSaveWithImageThenShouldReturnSavedProduct() throws ProductAlreadyExistsException, IOException {
        Product savedProduct = productController.saveProduct(asJsonString(product1), file).getBody();
        product1.setPhoto(new Photo(file.getOriginalFilename(), file.getContentType(), Base64.getEncoder().encodeToString(file.getBytes())));
        assertNotNull(savedProduct);
        assertEquals(product1, savedProduct);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenExistingProductToSaveWithImageThenShouldReturnSavedProduct() throws ProductAlreadyExistsException, IOException {
        product1.setPhoto(new Photo(file.getOriginalFilename(), file.getContentType(), Base64.getEncoder().encodeToString(file.getBytes())));
        String jsonProduct1 = asJsonString(product1);
        productController.saveProduct(jsonProduct1, file);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productController.saveProduct(jsonProduct1, file));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenExistingProductToSaveThenShouldNotReturnSavedProduct() throws ProductAlreadyExistsException, IOException {
        productController.saveProduct(asJsonString(product1), null);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productController.saveProduct(asJsonString(product1), null));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenGetAllProductsThenShouldReturnListOfAllProducts() throws ProductAlreadyExistsException, IOException {
        productController.saveProduct(asJsonString(product1), null).getBody();
        productController.saveProduct(asJsonString(product2), null).getBody();
        productController.saveProduct(asJsonString(product3), null).getBody();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        List<Product> products = productController.getAllProducts().getBody();
        assertNotNull(products);
        assertEquals(productList, products);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenProductIdThenShouldReturnRespectiveProduct() throws ProductAlreadyExistsException, ProductNotFoundException, IOException {
        Product savedProduct = productController.saveProduct(asJsonString(product1), null).getBody();
        productController.saveProduct(asJsonString(product2), null);
        productController.saveProduct(asJsonString(product3), null);
        Product getProduct = productController.getProductById(savedProduct.getId()).getBody();
        assertNotNull(getProduct);
        assertEquals(product1, getProduct);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductIdThenShouldNotReturnRespectiveProduct() throws ProductNotFoundException {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productController.getProductById(product1.getId()));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductIdToDeleteThenShouldReturnDeletedProduct() throws ProductAlreadyExistsException, ProductNotFoundException, IOException {
        Product savedProduct = productController.saveProduct(asJsonString(product1), null).getBody();
        productController.saveProduct(asJsonString(product2), null);
        productController.saveProduct(asJsonString(product3), null);
        Product deletedProduct = productController.deleteProduct(savedProduct.getId()).getBody();
        assertNotNull(deletedProduct);
        assertEquals(product1, deletedProduct);
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
    public void givenProductToUpdateThenShouldNotReturnUpdatedProduct() throws ProductAlreadyExistsException, ProductNotFoundException {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productController.updateProduct(asJsonString(product1), null));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenProductToUpdateThenShouldReturnUpdatedProduct() throws ProductAlreadyExistsException, ProductNotFoundException, IOException {
        Product savedProduct = productController.saveProduct(asJsonString(product1), null).getBody();
        assertNotNull(savedProduct);
        assertEquals(product1, savedProduct);
        savedProduct.setPrice(product2.getPrice());
        Product updatedProduct = productController.updateProduct(asJsonString(savedProduct), null).getBody();
        assertNotNull(savedProduct);
        assertEquals(savedProduct, updatedProduct);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenProductWithImageToUpdateThenShouldReturnUpdatedProduct() throws ProductAlreadyExistsException, IOException {
        Product savedProduct = productController.saveProduct(asJsonString(product1), file).getBody();
        assertNotNull(savedProduct);
        product1.setPhoto(new Photo(file.getOriginalFilename(), file.getContentType(), Base64.getEncoder().encodeToString(file.getBytes())));
        assertEquals(product1, savedProduct);
        Product updatedProduct = productController.updateProduct(asJsonString(savedProduct), file).getBody();
        assertNotNull(savedProduct);
        assertEquals(savedProduct, updatedProduct);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenProductWithImageToUpdateThenShouldNotReturnUpdatedProduct() throws ProductAlreadyExistsException, IOException {
        product1.setPhoto(new Photo(file.getOriginalFilename(), file.getContentType(), Base64.getEncoder().encodeToString(file.getBytes())));
        String jsonProduct1 = asJsonString(product1);
        productController.saveProduct(jsonProduct1, file);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productController.saveProduct(jsonProduct1, file));
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenGetAllProductsByCategoryThenShouldReturnListOfAllRespectiveProducts() throws IOException {
        product1.setCategory(product2.getCategory());
        productList.add(product1);
        productList.add(product2);
        productController.saveProduct(asJsonString(product1), null);
        productController.saveProduct(asJsonString(product2), null);
        productController.saveProduct(asJsonString(product3), null);
        List<Product> products = productController.getProductsByCategory(product2.getCategory()).getBody();
        assertNotNull(products);
        assertEquals(productList, products);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenGetAllHiddenProductsByCategoryThenShouldNotReturnListOfHiddenProducts() throws IOException {
        product2.setCategory(product3.getCategory());
        product2.setHidden(true);
        productController.saveProduct(asJsonString(product1), null);
        productController.saveProduct(asJsonString(product2), null);
        productController.saveProduct(asJsonString(product3), null);
        List<Product> products = productController.getProductsByCategory(product3.getCategory()).getBody();
        assertNotNull(products);
        assertEquals(productList, products);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenGetAllProductsByCategoryAdminThenShouldReturnListOfAllRespectiveProducts() throws IOException {
        product2.setCategory(product3.getCategory());
        product2.setHidden(true);
        productList.add(product2);
        productList.add(product3);
        productController.saveProduct(asJsonString(product1), null);
        productController.saveProduct(asJsonString(product2), null);
        productController.saveProduct(asJsonString(product3), null);
        List<Product> products = productController.getProductsByCategoryAdmin(product3.getCategory()).getBody();
        assertNotNull(products);
        assertEquals(productList, products);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    public void givenGetAllProductsByUserIdThenShouldReturnListOfAllRespectiveProducts() throws IOException {
        productList.add(product1);
        productList.add(product2);
        productController.saveProduct(asJsonString(product1), null);
        productController.saveProduct(asJsonString(product2), null);
        productController.saveProduct(asJsonString(product3), null);
        List<Product> products = productController.getProductsByUserId(product1.getUserId()).getBody();
        assertNotNull(products);
        assertEquals(productList, products);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    /******* VALIDATION *****/
    @Test
    void givenValidProductThenReturnRespectiveProduct() throws ProductAlreadyExistsException, IOException {
        Product savedProduct = productController.saveProduct(asJsonString(product1), null).getBody();
        assertEquals(product1, savedProduct);
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductWithInvalidIdToDeleteThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setId("");
            Product savedProduct = productController.saveProduct(asJsonString(product1), null).getBody();
            productController.deleteProduct(savedProduct.getId()).getBody();
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductWithInvalidIdToGetThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setId("");
            Product savedProduct = productController.saveProduct(asJsonString(product1), null).getBody();
            productController.getProductById(savedProduct.getId()).getBody();
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductWithInvalidCategoryThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setCategory("sdfsdfs");
            productController.saveProduct(asJsonString(product1), null);
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductWithInvalidPriceThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setPrice(-1);
            productController.saveProduct(asJsonString(product1), null);
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductWithInvalidTitleThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setTitle("");
            productController.saveProduct(asJsonString(product1), null);
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }

    @Test
    void givenProductWithInvalidUserIdThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setUserId(0);
            productController.saveProduct(asJsonString(product1), null);
        });
        assertTrue(logger.isInfoEnabled());
        assertTrue(logger.isErrorEnabled());
    }
}