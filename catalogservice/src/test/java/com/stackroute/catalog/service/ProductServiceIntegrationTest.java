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
public class ProductServiceIntegrationTest {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    private Product product1, product2, product3;
    private List<Product> productList;
    private MultipartFile file;

    @BeforeEach
    void setUp() {
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
        Product savedProduct = productService.saveProduct(product1, null);
        assertNotNull(savedProduct);
        assertEquals(product1, savedProduct);
    }

    @Test
    public void givenProductToSaveWithImageThenShouldReturnSavedProduct() throws ProductAlreadyExistsException, IOException {
        product1.setPhoto(new Photo(file.getOriginalFilename(), file.getContentType(), Base64.getEncoder().encodeToString(file.getBytes())));
        Product savedProduct = productService.saveProduct(product1, file);
        assertNotNull(savedProduct);
        assertEquals(product1, savedProduct);
    }

    @Test
    public void givenExistingProductToSaveWithImageThenShouldReturnSavedProduct() throws ProductAlreadyExistsException, IOException {
        product1.setPhoto(new Photo(file.getOriginalFilename(), file.getContentType(), Base64.getEncoder().encodeToString(file.getBytes())));
        productService.saveProduct(product1, file);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(product1, file));
    }

    @Test
    public void givenProductToSaveThenShouldNotReturnSavedProduct() throws ProductAlreadyExistsException, IOException {
        productService.saveProduct(product1, null);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(product1, null));
    }

    @Test
    public void givenProductWithDuplicateIdToSaveThenShouldNotReturnSavedProduct() throws ProductAlreadyExistsException, IOException {
        Product savedProduct = productService.saveProduct(product1, null);
        assertNotNull(savedProduct);
        product2.setId(product1.getId());
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(product2, null));
    }

    @Test
    public void givenGetAllProductsThenShouldReturnListOfAllProducts() throws ProductAlreadyExistsException, IOException {
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productService.saveProduct(product1, null);
        productService.saveProduct(product2, null);
        productService.saveProduct(product3, null);
        List<Product> products = productService.getAllProducts();
        assertNotNull(products);
        assertEquals(productList, products);
    }

    @Test
    public void givenProductIdThenShouldReturnRespectiveProduct() throws ProductAlreadyExistsException, ProductNotFoundException, IOException {
        Product savedProduct = productService.saveProduct(product1, null);
        productService.saveProduct(product2, null);
        productService.saveProduct(product3, null);
        Product getProduct = productService.getProductById(savedProduct.getId());
        assertNotNull(getProduct);
        assertEquals(product1, getProduct);
    }

    @Test
    void givenProductIdThenShouldNotReturnRespectiveProduct() throws ProductNotFoundException {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getProductById(product1.getId()));
    }

    @Test
    void givenProductIdToDeleteThenShouldReturnDeletedProduct() throws ProductAlreadyExistsException, ProductNotFoundException, IOException {
        Product savedProduct = productService.saveProduct(product1, null);
        productService.saveProduct(product2, null);
        productService.saveProduct(product3, null);
        Product deletedProduct = productService.deleteProduct(savedProduct.getId());
        assertNotNull(deletedProduct);
        assertEquals(product1, deletedProduct);
    }

    @Test
    void givenProductIdToDeleteThenShouldNotReturnDeletedProduct() throws ProductNotFoundException {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(product1.getId()));
    }

    @Test
    public void givenProductToUpdateThenShouldReturnUpdatedProduct() throws ProductAlreadyExistsException, ProductNotFoundException, IOException {
        Product savedProduct = productService.saveProduct(product1, null);
        assertNotNull(savedProduct);
        assertEquals(product1, savedProduct);
        savedProduct.setPrice(product2.getPrice());
        Product updatedProduct = productService.updateProduct(savedProduct, null);
        assertNotNull(savedProduct);
        assertEquals(savedProduct, updatedProduct);
    }

    @Test
    public void givenProductWithImageToUpdateThenShouldReturnUpdatedProduct() throws ProductAlreadyExistsException, ProductNotFoundException, IOException {
        Product savedProduct = productService.saveProduct(product1, null);
        assertNotNull(savedProduct);
        product1.setPhoto(new Photo(file.getOriginalFilename(), file.getContentType(), Base64.getEncoder().encodeToString(file.getBytes())));
        assertEquals(product1, savedProduct);
        Product updatedProduct = productService.updateProduct(savedProduct, file);
        assertNotNull(savedProduct);
        assertEquals(savedProduct, updatedProduct);
    }

    @Test
    public void givenProductWithImageToUpdateThenShouldNotReturnUpdatedProduct() throws ProductAlreadyExistsException, IOException {
        product1.setPhoto(new Photo(file.getOriginalFilename(), file.getContentType(), Base64.getEncoder().encodeToString(file.getBytes())));
        productService.saveProduct(product1, file);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(product1, file));
    }

    @Test
    public void givenProductToUpdateThenShouldNotReturnUpdatedProduct() throws ProductNotFoundException {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(product1, null));
    }

    @Test
    public void givenGetAllProductsByCategoryThenShouldReturnListOfAllRespectiveProducts() throws IOException {
        product1.setCategory(product2.getCategory());
        productList.add(product1);
        productList.add(product2);
        productService.saveProduct(product1, null);
        productService.saveProduct(product2, null);
        productService.saveProduct(product3, null);
        List<Product> products = productService.getProductsByCategory(product2.getCategory());
        assertNotNull(products);
        assertEquals(productList, products);
    }

    @Test
    public void givenGetAllProductsByUserIdThenShouldReturnListOfAllRespectiveProducts() throws IOException {
        productList.add(product1);
        productList.add(product2);
        productService.saveProduct(product1, null);
        productService.saveProduct(product2, null);
        productService.saveProduct(product3, null);
        List<Product> products = productService.getProductsByUserId(product1.getUserId());
        assertNotNull(products);
        assertEquals(productList, products);
    }

    /******* VALIDATION *****/

    @Test
    void givenValidProductThenReturnRespectiveProduct() throws ProductAlreadyExistsException, IOException {
        Product savedProduct = productService.saveProduct(product1, null);
        assertEquals(product1, savedProduct);
    }

    @Test
    void givenProductWithInvalidIdToDeleteThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setId("");
            Product savedProduct = productService.saveProduct(product1, null);
            productService.deleteProduct(savedProduct.getId());
        });
    }

    @Test
    void givenProductWithInvalidIdToGetThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setId("");
            Product savedProduct = productService.saveProduct(product1, null);
            productService.getProductById(savedProduct.getId());
        });
    }

    @Test
    void givenProductWithInvalidTitleThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setTitle("");
            productService.saveProduct(product1, null);
        });
    }

    @Test
    void givenProductWithInvalidPriceThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setPrice(-1);
            productService.saveProduct(product1, null);
        });
    }

    @Test
    void givenProductWithInvalidCategoryThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setCategory("zfddf");
            productService.saveProduct(product1, null);
        });
    }

    @Test
    void givenProductWithInvalidUserIdThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setUserId(0);
            productService.saveProduct(product1, null);
        });
    }
}