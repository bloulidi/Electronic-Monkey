package com.stackroute.catalog.service;

import com.stackroute.catalog.exception.ProductAlreadyExistsException;
import com.stackroute.catalog.exception.ProductNotFoundException;
import com.stackroute.catalog.model.Product;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

@Validated
public interface ProductService {
    Product saveProduct(@Valid Product product, MultipartFile image) throws ProductAlreadyExistsException, IOException;

    Product getProductById(@NotBlank(message = "ID cannot be empty.") String id) throws ProductNotFoundException;

    Product deleteProduct(@NotBlank(message = "ID cannot be empty.") String id) throws ProductNotFoundException;

    Product updateProduct(@Valid Product product, MultipartFile image) throws ProductNotFoundException, IOException;

    List<Product> getProductsByUserId(long userId);

    List<Product> getProductsByCategory(String category);

    List<Product> getAllProducts();
}