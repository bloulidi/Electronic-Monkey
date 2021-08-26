package com.stackroute.catalog.service;

import com.stackroute.catalog.exception.ProductAlreadyExistsException;
import com.stackroute.catalog.exception.ProductNotFoundException;
import com.stackroute.catalog.model.Category;
import com.stackroute.catalog.model.Product;
import com.stackroute.catalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) throws ProductAlreadyExistsException {
        if(product.getId() != null && !product.getId().isBlank()){
            if(productRepository.existsById(product.getId())){
                throw new ProductAlreadyExistsException();
            }
        }
        if(productRepository.existsByCode(product.getCode())){
            throw new ProductAlreadyExistsException();
        }
        return (Product) productRepository.save(product);
    }

    @Override
    public Product getProductById(String id) throws ProductNotFoundException {
        Product product = null;
        Optional<Product> userOptional = productRepository.findById(id);
        if(userOptional.isPresent()){
            product = userOptional.get();
        } else {
            throw new ProductNotFoundException();
        }
        return product;
    }

    @Override
    public Product getProductByCode(String code) throws ProductNotFoundException {
        Product product = productRepository.findByCode(code);
        if(product == null){
            throw new ProductNotFoundException();
        }
        return product;
    }

    @Override
    public Product deleteProduct(String id) throws ProductNotFoundException {
        Product product = getProductById(id);
        if(product != null){
            productRepository.deleteById(id);
        } else {
            throw new ProductNotFoundException();
        }
        return product;
    }

    @Override
    public Product updateProduct(Product product) throws ProductNotFoundException {
        if(!productRepository.existsById(product.getId())){
            throw new ProductNotFoundException();
        }
        return (Product) productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }
}
