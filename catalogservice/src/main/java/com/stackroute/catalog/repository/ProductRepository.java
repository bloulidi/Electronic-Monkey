package com.stackroute.catalog.repository;

import com.stackroute.catalog.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    //boolean existsByCode(String code);
    //Product findByCode(String code);
}