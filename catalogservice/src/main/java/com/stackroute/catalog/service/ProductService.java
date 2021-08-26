package com.stackroute.catalog.service;

import com.stackroute.catalog.exception.ProductAlreadyExistsException;
import com.stackroute.catalog.exception.ProductNotFoundException;
import com.stackroute.catalog.model.Product;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface ProductService {
    Product saveProduct(@Valid Product product) throws ProductAlreadyExistsException;
    Product getProductById(@NotBlank(message = "ID cannot be empty.") String id) throws ProductNotFoundException;
    Product getProductByCode(@NotBlank(message = "Code cannot be empty.") String code) throws ProductNotFoundException;
    Product deleteProduct(@NotBlank(message = "ID cannot be empty.") String id) throws ProductNotFoundException;
    Product updateProduct(@Valid Product product) throws ProductNotFoundException;
    List<Product> getAllProducts();
}
