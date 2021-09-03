package com.stackroute.catalog.controller;

import com.stackroute.catalog.exception.ProductAlreadyExistsException;
import com.stackroute.catalog.exception.ProductNotFoundException;
import com.stackroute.catalog.model.Product;
import com.stackroute.catalog.service.ProductService;
import com.stackroute.catalog.swagger.SpringFoxConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/products")
@Api(tags = { SpringFoxConfig.PRODUCT_TAG })
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    @ApiOperation("Creates a new product with an image.")
    public ResponseEntity<Product> saveProduct(@ApiParam("Product information for a new product to be created. 409 if already exists.") @RequestPart String product, @ApiParam("Image information for a new product to be created.") @RequestPart MultipartFile image) throws ProductAlreadyExistsException, IOException{
        log.info("Create a new product with image=" + image.getOriginalFilename() + ": " + product);
        return new ResponseEntity<Product>(productService.saveProduct(product, image), HttpStatus.CREATED);
    }

    @PostMapping("noimage")
    @ApiOperation("Creates a new product without an image.")
    public ResponseEntity<Product> saveProduct(@ApiParam("Product information for a new product to be created. 409 if already exists.") @RequestBody Product product) throws ProductAlreadyExistsException {
        log.info("Create a new product: " + product.toString());
        return new ResponseEntity<Product>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation("Returns list of all products in the system.")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Return list of all products in the system.");
        return new ResponseEntity<List<Product>>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiOperation("Returns a specific product by their identifier. 404 if does not exist.")
    public ResponseEntity<Product> getProductById(@ApiParam("Id of the product to be obtained. Cannot be empty.")  @PathVariable String id) throws ProductNotFoundException {
        log.info("Return product with id = " + id);
        return new ResponseEntity<Product>(productService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    @ApiOperation("Returns a list of products by their userId. 404 if does not exist.")
    public ResponseEntity<List<Product>> getProductsByUserId(@ApiParam("UserId associated to the list of products to be obtained.") @PathVariable long userId) throws ProductNotFoundException {
        log.info("Return products with userId = " + userId);
        return new ResponseEntity<List<Product>>(productService.getProductsByUserId(userId), HttpStatus.OK);
    }
    @GetMapping("category/{category}")
    @ApiOperation("Returns a list of products by their userId. 404 if does not exist.")
    public ResponseEntity<List<Product>> getProductsByCategory(@ApiParam("Category associated to the list of product to be obtained. Cannot be empty.") @PathVariable String category) throws ProductNotFoundException {
        log.info("Return products with category = " + category);
        return new ResponseEntity<List<Product>>(productService.getProductsByCategory(category), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    @ApiOperation("Deletes a product from the system. 404 if the person's identifier is not found.")
    public ResponseEntity<Product> deleteProduct(@ApiParam("Id of the product to be deleted. Cannot be empty.") @PathVariable String id) throws ProductNotFoundException {
        log.info("Delete product with id = " + id);
        return new ResponseEntity<Product>(productService.deleteProduct(id), HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation("Updates a product.")
    public ResponseEntity<Product> updateProduct(@ApiParam("Product information for a product to be updated. 404 if does not exist.") @RequestBody Product product) throws ProductNotFoundException {
        log.info("Update product with id: " + product.getId());
        return new ResponseEntity<Product>(productService.updateProduct(product), HttpStatus.OK);
    }

    @PatchMapping(value = "image", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    @ApiOperation("Updates a product with an image.")
    public ResponseEntity<Product> updateProduct(@ApiParam("Product information for a product to be updated. 404 if does not exist.") @RequestPart String product, @ApiParam("Image information for updated product.") @RequestPart MultipartFile image) throws ProductNotFoundException, IOException{
        log.info("Update product with image: " + image.getOriginalFilename());
        return new ResponseEntity<Product>(productService.updateProduct(product, image), HttpStatus.OK);
    }
}
