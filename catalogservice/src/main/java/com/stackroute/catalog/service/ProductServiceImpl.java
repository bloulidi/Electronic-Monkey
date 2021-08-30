package com.stackroute.catalog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.catalog.exception.ProductAlreadyExistsException;
import com.stackroute.catalog.exception.ProductNotFoundException;
import com.stackroute.catalog.model.Photo;
import com.stackroute.catalog.model.Product;
import com.stackroute.catalog.repository.ProductRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        return (Product) productRepository.insert(product);
    }

    @Override
    public Product saveProduct(String product, MultipartFile image) throws ProductAlreadyExistsException, IOException {
        Product productJson = new Product();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            productJson = objectMapper.readValue(product, Product.class);
        } catch (IOException e){
            System.out.println("Error in mapping Product string to POJO");
            e.printStackTrace();
        }
        if(productJson.getId() != null && !productJson.getId().isBlank()){
            if(productRepository.existsById(productJson.getId())){
                throw new ProductAlreadyExistsException();
            }
        }

        Photo photo = new Photo();
        photo.setTitle(image.getOriginalFilename());
        photo.setType(image.getContentType());
        photo.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
        productJson.setPhoto(photo);

        return (Product) productRepository.insert(productJson);
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
    public Product updateProduct(Product product) throws ProductNotFoundException, ProductAlreadyExistsException {
        if(!productRepository.existsById(product.getId())){
            throw new ProductNotFoundException();
        }
        Product getProduct = getProductById(product.getId());
        product.setPhoto(getProduct.getPhoto());
        return (Product) productRepository.save(product);
    }

    @Override
    public List<Product> getProductsByUserId(long userId)  {
        return (List<Product>) productRepository.findByUserId(userId);
    }

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }
}
