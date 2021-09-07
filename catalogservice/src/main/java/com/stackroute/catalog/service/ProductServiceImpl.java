package com.stackroute.catalog.service;

import com.stackroute.catalog.exception.ProductAlreadyExistsException;
import com.stackroute.catalog.exception.ProductNotFoundException;
import com.stackroute.catalog.model.Photo;
import com.stackroute.catalog.model.Product;
import com.stackroute.catalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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
    public Product saveProduct(Product product, MultipartFile image) throws ProductAlreadyExistsException, IOException {
        if (product.getId() != null && !product.getId().isBlank()) {
            if (productRepository.existsById(product.getId())) {
                throw new ProductAlreadyExistsException();
            }
        }

        Photo photo = new Photo();
        if (image != null) {
            if (!image.isEmpty() || image.getSize() != 0) {
                photo.setTitle(image.getOriginalFilename());
                photo.setType(image.getContentType());
                photo.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
        }
        product.setPhoto(photo);

        return (Product) productRepository.insert(product);
    }

    @Override
    public Product getProductById(String id) throws ProductNotFoundException {
        Product product = null;
        Optional<Product> userOptional = productRepository.findById(id);
        if (userOptional.isPresent()) {
            product = userOptional.get();
        } else {
            throw new ProductNotFoundException();
        }
        return product;
    }

    @Override
    public Product deleteProduct(String id) throws ProductNotFoundException {
        Product product = getProductById(id);
        if (product != null) {
            productRepository.deleteById(id);
        }
        return product;
    }

    @Override
    public Product updateProduct(Product product, MultipartFile image) throws ProductNotFoundException, IOException {
        if (!productRepository.existsById(product.getId())) {
            throw new ProductNotFoundException();
        }

        if (image != null) {
            if (!image.isEmpty() || image.getSize() != 0) {
                Photo photo = new Photo();
                photo.setTitle(image.getOriginalFilename());
                photo.setType(image.getContentType());
                photo.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
                product.setPhoto(photo);
            }
        }

        return (Product) productRepository.save(product);
    }

    @Override
    public List<Product> getProductsByUserId(long userId) {
        return (List<Product>) productRepository.findByUserId(userId);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return (List<Product>) productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }
}