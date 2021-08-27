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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import java.util.ArrayList;
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
    //private List<String> productsCode, savedProductsCode;

    @BeforeEach
    void setUp() {
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
        Product savedProduct = productService.saveProduct(product1);
        assertNotNull(savedProduct);
        assertEquals(product1.getId(), savedProduct.getId());
    }

    @Test
    public void givenProductToSaveThenShouldNotReturnSavedProduct() throws ProductAlreadyExistsException {
        productService.saveProduct(product1);
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(product1));
    }

    @Test
    public void givenProductWithDuplicateIdToSaveThenShouldNotReturnSavedProduct() throws ProductAlreadyExistsException {
        Product savedProduct = productService.saveProduct(product1);
        assertNotNull(savedProduct);
        product2.setId(product1.getId());
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(product2));
    }

    /*@Test
    public void givenProductWithDuplicateCodeToSaveThenShouldNotReturnSavedProduct() throws ProductAlreadyExistsException {
        Product savedProduct = productService.saveProduct(product1);
        assertNotNull(savedProduct);
        product2.setCode(product1.getCode());
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(product2));
    }*/

    @Test
    public void givenGetAllProductsThenShouldReturnListOfAllProducts() throws ProductAlreadyExistsException {
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productService.saveProduct(product1);
        productService.saveProduct(product2);
        productService.saveProduct(product3);
        List<Product> products = productService.getAllProducts();
        //for (Product product : productList) { productsCode.add(product.getCode()); }
        //for (Product product : products) { savedProductsCode.add(product.getCode()); }
        assertNotNull(products);
        assertEquals(productList, products);
    }

    @Test
    public void givenProductIdThenShouldReturnRespectiveProduct() throws ProductAlreadyExistsException, ProductNotFoundException {
        Product savedProduct = productService.saveProduct(product1);
        productService.saveProduct(product2);
        productService.saveProduct(product3);
        Product getProduct = productService.getProductById(savedProduct.getId());
        assertNotNull(getProduct);
        assertEquals(product1.getId(), getProduct.getId());
    }

    @Test
    void givenProductIdThenShouldNotReturnRespectiveProduct() throws ProductNotFoundException {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getProductById(product1.getId()));
    }

    /*@Test
    public void givenProductCodeThenShouldReturnRespectiveProduct() throws ProductAlreadyExistsException, ProductNotFoundException {
        Product savedProduct = productService.saveProduct(product1);
        productService.saveProduct(product2);
        productService.saveProduct(product3);
        Product getProduct = productService.getProductByCode(savedProduct.getCode());
        assertNotNull(getProduct);
        assertEquals(product1.getCode(), getProduct.getCode());
    }

    @Test
    void givenProductCodeThenShouldNotReturnRespectiveProduct() throws ProductNotFoundException {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.getProductByCode(product1.getCode()));
    }*/

    @Test
    void givenProductIdToDeleteThenShouldReturnDeletedProduct() throws ProductAlreadyExistsException, ProductNotFoundException {
        Product savedProduct = productService.saveProduct(product1);
        productService.saveProduct(product2);
        productService.saveProduct(product3);
        Product deletedProduct = productService.deleteProduct(savedProduct.getId());
        assertNotNull(deletedProduct);
        assertEquals(product1.getId(), deletedProduct.getId());
    }

    @Test
    void givenProductIdToDeleteThenShouldNotReturnDeletedProduct() throws ProductNotFoundException {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(product1.getId()));
    }

    @Test
    public void givenProductToUpdateThenShouldReturnUpdatedProduct() throws ProductAlreadyExistsException, ProductNotFoundException {
        Product savedProduct = productService.saveProduct(product1);
        assertNotNull(savedProduct);
        assertEquals(product1.getId(), savedProduct.getId());;
        savedProduct.setPrice(product2.getPrice());
        Product updatedProduct = productService.updateProduct(savedProduct);
        assertNotNull(savedProduct);
        assertEquals(savedProduct, updatedProduct);
    }

    /*@Test
    public void givenProductToUpdateWithAlreadyExistingCodeThenShouldNotReturnUpdatedProduct() throws ProductNotFoundException, ProductAlreadyExistsException {
        Product savedProduct = productService.saveProduct(product1);
        productService.saveProduct(product2);
        assertNotNull(savedProduct);
        assertEquals(product1.getCode(), savedProduct.getCode());;
        savedProduct.setCode(product2.getCode());
        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.updateProduct(savedProduct));
    }*/

    @Test
    public void givenProductToUpdateThenShouldNotReturnUpdatedProduct() throws ProductNotFoundException {
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(product1));
    }

    /******* VALIDATION *****/

    @Test
    void givenValidProductThenReturnRespectiveProduct() throws ProductAlreadyExistsException {
        Product savedProduct = productService.saveProduct(product1);
        assertEquals(product1.getId(), savedProduct.getId());
    }

    @Test
    void givenProductWithInvalidIdToDeleteThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setId("");
            Product savedProduct = productService.saveProduct(product1);
            productService.deleteProduct(savedProduct.getId());
        });
    }

    @Test
    void givenProductWithInvalidIdToGetThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setId("");
            Product savedProduct = productService.saveProduct(product1);
            productService.getProductById(savedProduct.getId());
        });
    }

    /*@Test
    void givenProductWithInvalidCodeThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setCode("");
            productService.saveProduct(product1);
        });
    }*/

    @Test
    void givenProductWithInvalidTitleThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setTitle("");
            productService.saveProduct(product1);
        });
    }

    @Test
    void givenProductWithInvalidCategoryThenThrowsException() throws ProductAlreadyExistsException, ConstraintViolationException {
        assertThrows(ConstraintViolationException.class, () -> {
            product1.setCategory("zfddf");
            productService.saveProduct(product1);
        });
    }
}