package com.stackroute.catalog;

import com.stackroute.catalog.controller.ProductController;
import com.stackroute.catalog.repository.ProductRepository;
import com.stackroute.catalog.service.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CatalogApplicationTests {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        assertThat(productService).isNotNull();
        assertThat(productController).isNotNull();
        assertThat(productRepository).isNotNull();
    }
}